package com.segroup.seproject_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

import static org.apache.commons.io.FileUtils.readFileToByteArray;
import static org.apache.commons.io.FileUtils.readFileToString;

@Controller
public class ImageRecogController {

    @Autowired
    private Environment environment;


    private byte byteBuffer[];
    @PostMapping("/use/image")
    @ResponseBody
    public PredictResultItem handleFileUpload(FileUploadItem fileUploadItem) throws IOException, InterruptedException {
        // 保存文件
        Date date = new Date();
        String fileName = date.getTime() + fileUploadItem.getPostfix();
        String filePathName = environment.getProperty("my-config.image-recog.save-path") + fileName;
        File file = new File(filePathName);
        //File parentfile = file.getParentFile();
        //System.out.println(parentfile.exists());
        if (!(file.createNewFile())) {
            throw new IOException("ImageRecogController.handleFileUpload: 文件已存在！");
        }
        fileUploadItem.getFile().transferTo(file);
        System.out.println("成功接收到文件，保存在" + filePathName);

        //传给python模型
        String predictScriptPathname = environment.getProperty("my-config.global.pyscript-path") + "predict.py";
        String modelPathname = environment.getProperty("my-config.global.model-path");
        ArrayList<String> command = new ArrayList(Arrays.asList("python", predictScriptPathname, "--image", filePathName, "--model", modelPathname));
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.inheritIO();
        System.out.println("----------------预测开始----------------");
        Process process = processBuilder.start();
        process.waitFor();
        System.out.println("----------------预测完成----------------");

        //接收结果并返回
        String resultImagePathName = filePathName + "_pred";
        String resultTextPathName = "D:\\test_file_recv\\1698677748104.png_pred.txt"; //明天修改了脚本之后再改
        PredictResultItem predictResultItem = new PredictResultItem();
        //  读取图片和文本文件
        File resultImage = new File(resultImagePathName);
        byte[] imageData = readFileToByteArray(resultImage);
        File resultText = new File(resultTextPathName);
        String textString = readFileToString(resultText, (Charset) null);
        //  写入结果对象
        predictResultItem.setFile(Base64.getEncoder().encodeToString(imageData));
        predictResultItem.setText(textString);

        return predictResultItem;
    }
}
