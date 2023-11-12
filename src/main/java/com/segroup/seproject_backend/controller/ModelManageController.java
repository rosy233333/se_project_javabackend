package com.segroup.seproject_backend.controller;

import com.segroup.seproject_backend.data_item.ResultWebItem;
import com.segroup.seproject_backend.data_item.SwitchModelF2JWebItem;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

}
