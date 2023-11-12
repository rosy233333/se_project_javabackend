package com.segroup.seproject_backend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.segroup.seproject_backend.config.TrainModelF2JEndpoint;
import com.segroup.seproject_backend.config.TrainModelJ2PClient;
import com.segroup.seproject_backend.data_item.*;
import com.segroup.seproject_backend.repository.ProjectRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;

// 专门用于训练模型
@Controller
public class ModelTrainController {

    // 与前端通信
    private TrainModelF2JEndpoint f2jEndpoint;
    // 与python后端通信
    private TrainModelJ2PClient j2pClient;

    // 操作数据库的类
    @Autowired
    private ProjectRepo repo;

    //用于获取配置文件
    @Autowired
    private Environment environment;

    // 正在训练的模型的数据类
    // 同时，其是否为null表明是否有训练任务正在进行
    private ModelDBItem model;

    public ModelTrainController() {
        f2jEndpoint = new TrainModelF2JEndpoint();
        j2pClient = new TrainModelJ2PClient();
        f2jEndpoint.controller = this;
        j2pClient.controller = this;

        model = null;
    }

    // 前端开始训练，调用此函数
    public void onFStart(TrainStartF2JWebItem body) throws IOException {
        if(model != null) {
            System.out.println("错误：有模型正在训练中");
            return;
        }
        if(j2pClient.isOpen()) {
            System.out.println("876trdfyujhgfdszxcvbngfd");
            return;
        }

        // 实例化模型的数据类
        model = new ModelDBItem();
        model.setUser_id(body.getUser_id());
        model.setModel_name(body.getModel_name());
        model.setDataset_id(body.getDataset_id());
        model.setIs_active(0);
        model.setModel_create_date(new Date());
        model.setModel_activate_date(null);

        // 获取数据集内容
        long dataset_id = body.getDataset_id();
        List<ImageDBItem> image_list = repo.findImagesByDatasetId(dataset_id);

        // 创建数据集文件
        Date date = new Date();
        String filePathName = environment.getProperty("my-config.dataset-file-save-path") + "dataset" + date.getTime() + ".txt";
        File file = new File(filePathName);
        if (!(file.createNewFile())) {
            throw new IOException("ModelTrainController.onFStart: 无法创建文件，文件已存在！");
        }
        FileWriter fileWriter = new FileWriter(file);
        for (ImageDBItem image: image_list) {
            String write_str = String.format("%s, %d\n", image.getImage_path(), image.getLabel());
            fileWriter.append(write_str);
        }
        fileWriter.close();
        System.out.println("创建数据集文件成功。");

        // 准备传递给python后端的数据类，并将其序列化
        TrainStartJ2PWebItem toPData = new TrainStartJ2PWebItem(
                filePathName,
                body.getRatio(),
                body.getEpoch(),
                body.getBatch_size(),
                body.getLearning_rate()
            );
        WebSocketItem<TrainStartJ2PWebItem> toPItem = new WebSocketItem<>("start", toPData);
        ObjectMapper mapper = new ObjectMapper();
        String toPStr = mapper.writeValueAsString(toPItem);

        // 与python后端建立连接，并发送信息给python后端
        j2pClient.connect();
        j2pClient.send(toPStr);
    }

    // 前端停止训练，调用此函数
    public void onFStop() throws JsonProcessingException {
        // 此时，已经连接python后端
        if(j2pClient.isClosed()) {
            System.out.println("0987ytfghjitrdcvbnjuytf");
            return;
        }

        // 直接将消息转发到python后端
        WebSocketItem<Object> toPItem = new WebSocketItem<>("stop", null);
        ObjectMapper mapper = new ObjectMapper();
        String toPStr = mapper.writeValueAsString(toPItem);
        j2pClient.send(toPStr);
    }

    // python后端发来训练信息，调用此函数
    public void onPInfo(TrainInfoWebItem body) throws IOException {
        //更新模型数据类的准确率
        model.setTrain_accuracy(body.getAcc());

        // 将消息转发到前端
        WebSocketItem<TrainInfoWebItem> toFItem = new WebSocketItem<>("stop", body);
        ObjectMapper mapper = new ObjectMapper();
        String toFStr = mapper.writeValueAsString(toFItem);
        f2jEndpoint.send(toFStr);
    }

    // python后端训练完成，调用此函数
    public void onPFinish(TrainFinishP2JWebItem body) throws IOException {
        String model_path = body.getPath();
        //记录模型路径
        model.setModel_path(model_path);
        //在数据库中保存模型
        repo.insertModel(model);
        //断开和后端的连接
        j2pClient.close();
        //清空模型数据类
        model = null;

        // 将消息发送到前端
        WebSocketItem<Object> toFItem = new WebSocketItem<>("finish", null);
        ObjectMapper mapper = new ObjectMapper();
        String toFStr = mapper.writeValueAsString(toFItem);
        f2jEndpoint.send(toFStr);
    }
}

