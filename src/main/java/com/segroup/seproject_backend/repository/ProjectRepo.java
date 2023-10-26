package com.segroup.seproject_backend.repository;

import java.util.Date;

public interface ProjectRepo {

    //以下三个函数分别在数据库的使用和反馈表（usages）中登记模型的使用和反馈情况，调用一个函数则对应次数+1。
    public void recordOneUse(Date use_date, long model_id);
    public void recordOneRightFeedback(Date use_date, long model_id);
    public void recordOneWrongFeedback(Date use_date, long model_id);
}
