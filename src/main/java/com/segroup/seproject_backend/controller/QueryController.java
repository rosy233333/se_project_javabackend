package com.segroup.seproject_backend.controller;

import com.segroup.seproject_backend.data_item.QueryDatasetWebItem;
import com.segroup.seproject_backend.data_item.QueryModelWebItem;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

//这个类用来处理前端对模型列表、数据集列表的请求。
@Controller
public class QueryController {
    //对应接口：应用模型页面-请求模型列表
    @PostMapping("/request/model")
    @ResponseBody
    public QueryModelWebItem handleModelQuery() {
        // 需要你们来实现
        return null;
    }

    //对应接口：训练模型页面-请求数据集列表、请求数据集列表
    @PostMapping("/request/dataset")
    @ResponseBody
    public QueryDatasetWebItem handleDatasetQuery() {
        // 需要你们来实现
        return null;
    }
}
