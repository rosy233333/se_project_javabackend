package com.segroup.seproject_backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.IOException;
import java.util.Date;

@Controller
public class TestController {
    // 处理图片上传
//    @PostMapping("/use/image")
//    @ResponseBody
//    public String handleFileUpload(FileItem fileitem) throws IOException {
//        Date date = new Date();
//        String fileName = date.getTime() + ".png";
//        String filePathName = "D:\\test_file_recv\\" + fileName;
//        File file = new File(filePathName);
//        File parentfile = file.getParentFile();
//        System.out.println(parentfile.exists());
//        file.createNewFile();
//        fileitem.getFile().transferTo(file);
//        String result = "成功接收到文件，保存在" + filePathName;
//        System.out.println(result);
//        return result;
//    }


}
