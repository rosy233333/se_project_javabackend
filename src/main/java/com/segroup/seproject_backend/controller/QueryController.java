package com.segroup.seproject_backend.controller;

import com.segroup.seproject_backend.data_item.DatasetDBItem;
import com.segroup.seproject_backend.data_item.QueryDatasetWebItem;
import com.segroup.seproject_backend.data_item.QueryModelWebItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

//这个类用来处理前端对模型列表、数据集列表的请求。
@Controller
public class QueryController {
    @Autowired
    private JdbcTemplate jdbc;
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
    @CrossOrigin
    public QueryDatasetWebItem handleDatasetQuery() {
//        System.out.println("已获取数据集初始化请求");
        // 需要你们来实现
        QueryDatasetWebItem res = new QueryDatasetWebItem();
        List<DatasetDBItem> datasets;

        try{
            datasets = jdbc.query("SELECT * FROM datasets",
                    new BeanPropertyRowMapper<>(DatasetDBItem.class));
        }
        catch (EmptyResultDataAccessException e){
            datasets = null;
        }
        res.setDatasets(datasets);
//        System.out.println(res.getDatasets().get(0));
        return res;

    }
}
