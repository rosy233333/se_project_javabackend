package com.segroup.seproject_backend.repository;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

//使用lombok的注解，自动生成getter、setter等方法
@Data
@AllArgsConstructor
public class UsageItem {
    private Date use_date;
    private long model_id;
    private int use_count;
    private int wrong_feedback_count;
    private int right_feedback_count;
}
