package com.segroup.seproject_backend.data_item;

import lombok.Data;

import java.util.Date;

@Data
public class ModelDBItem {
    private long model_id;
    private long user_id;
    private String model_name;
    private String model_path;
    private long dataset_id;
    private double train_accuracy;
    private int is_active;
    private Date model_create_date;
    private Date model_activate_date;
}
