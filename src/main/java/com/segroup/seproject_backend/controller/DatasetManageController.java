package com.segroup.seproject_backend.controller;

import com.segroup.seproject_backend.data_item.DeleteDatasetWebItem;
import com.segroup.seproject_backend.data_item.ResultWebItem;
import com.segroup.seproject_backend.data_item.SwitchModelF2JWebItem;
import com.segroup.seproject_backend.data_item.UploadDatasetF2JWebItem;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DatasetManageController {
    //对应接口：删除数据集
    @PostMapping("/manage/delete_dataset")
    @ResponseBody
    public ResultWebItem handleDeleteDataset(DeleteDatasetWebItem body) {
        // 需要你们来实现
        return null;
    }

    //对应接口：上传数据集
    @PostMapping("/manage/upload_dataset")
    @ResponseBody
    public ResultWebItem handleUploadDataset(UploadDatasetF2JWebItem body) {
        // 需要你们来实现
        return null;
    }
}
