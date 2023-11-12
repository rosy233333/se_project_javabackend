package com.segroup.seproject_backend.data_item;

import lombok.Data;

@Data
public class TrainInfoWebItem {
    private int epoch;
    private double loss;
    private double acc;
}
