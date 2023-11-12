package com.segroup.seproject_backend.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.segroup.seproject_backend.controller.ModelTrainController;
import com.segroup.seproject_backend.data_item.TrainFinishP2JWebItem;
import com.segroup.seproject_backend.data_item.TrainInfoWebItem;
import com.segroup.seproject_backend.data_item.TrainStartF2JWebItem;
import com.segroup.seproject_backend.data_item.WebSocketItem;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.springframework.stereotype.Component;

import java.net.URI;

// 训练模型时的websocket客户端，用于和python后端连接
@Component
public class TrainModelJ2PClient extends WebSocketClient {
    // 初始化时，连接python后端
    public TrainModelJ2PClient() {
        super(URI.create("ws://localhost:8088"));
    }

    public ModelTrainController controller;

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        System.out.println("WebSocket：和python后端连接成功。");
    }

    @Override
    public void onMessage(String text) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            WebSocketItem<Object> object = mapper.readValue(text, new TypeReference<>(){});
            if(object.getType().equals("info")) {
                TrainInfoWebItem infoItem = mapper.readValue(text, new TypeReference<WebSocketItem<TrainInfoWebItem>>(){}).getObject();
                controller.onPInfo(infoItem);
            }
            else if (object.getType().equals("finish")) {
                TrainFinishP2JWebItem finishItem = mapper.readValue(text, new TypeReference<WebSocketItem<TrainFinishP2JWebItem>>(){}).getObject();
                controller.onPFinish(finishItem);
            }
            else {
                System.out.println("WebSocket错误：python后端发送的消息格式出错");
            }
        }
        catch (Exception e) {
            System.out.println("出现异常：");
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void onClose(int i, String s, boolean b) {
        System.out.println("WebSocket：和python后端连接关闭");
    }

    @Override
    public void onError(Exception e) {
        System.out.println(e.getMessage());
    }
}
