package com.segroup.seproject_backend.data_item;

import lombok.Data;

@Data
public class TrainModelF2JWebItem {
    private String model_name;
    private String dataset_id;
    private String ratio;
    private String epoch;
    private String batch_size;
    private String learning_rate;
}
