package com.segroup.seproject_backend.controller;

import com.segroup.seproject_backend.data_item.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

//这个类用来处理模型管理相关的功能
@Controller
public class ModelManageController {
    //对应接口：应用模型页面-更换/应用模型
    @PostMapping("/manage/switch_model")
    @ResponseBody
    public ResultWebItem handleSwitchModel(SwitchModelF2JWebItem body) {
        // 需要你们来实现
        return null;
    }

    //对应接口：训练模型页面-提交训练任务
    @PostMapping("/manage/train")
    @ResponseBody
    public Object handleTrainModel(TrainModelF2JWebItem body) {
        // 这个我来吧
        return null;
    }

}
