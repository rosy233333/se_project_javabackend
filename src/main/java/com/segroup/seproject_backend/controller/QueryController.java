package com.segroup.seproject_backend.controller;

import com.segroup.seproject_backend.data_item.ModelDBItem;
import com.segroup.seproject_backend.data_item.QueryDatasetWebItem;
import com.segroup.seproject_backend.data_item.QueryModelWebItem;
import com.segroup.seproject_backend.data_item.UsageDBItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


//这个类用来处理前端对模型列表、数据集列表的请求。
@RestController
public class QueryController {
    @Autowired
    private JdbcTemplate jdbc;
    //对应接口：应用模型页面-请求模型列表
    @PostMapping("/request/model")
    @CrossOrigin
    public QueryModelWebItem handleModelQuery() {//目前问题表是空的，跑不动
        QueryModelWebItem res = null;
        List<ModelDBItem> models;
        ModelDBItem cmodel = null;
        try{
            models = jdbc.queryForList("SELECT * FROM models",
                    ModelDBItem.class);
        }
        catch (EmptyResultDataAccessException e){
            models = null;
        }
        res.setModels(models);
        res.setCurrent_model(cmodel);
        return res;
    }

    //对应接口：训练模型页面-请求数据集列表、请求数据集列表
    @PostMapping("/request/dataset")
    @CrossOrigin
    public QueryDatasetWebItem handleDatasetQuery() {
        // 需要你们来实现
        return null;
    }
}
