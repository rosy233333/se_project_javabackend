package com.segroup.seproject_backend.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

@Repository
public class ProjectRepoImpl implements ProjectRepo{

    @Autowired
    private JdbcTemplate jdbc;

    //为使用频次表添加记录项
    private UsageItem insertUsageRecord(Date use_date, long model_id) {
        jdbc.update("INSERT INTO usages(use_date, model_id) VALUES (?, ?)", dateConvert(use_date), model_id);
        return new UsageItem(use_date, model_id, 0, 0, 0);
    }

    //用于将查询的行结果转化为数据类
    private UsageItem usageItemMapper(ResultSet rs, int rowNum) throws SQLException {
        return new UsageItem(
                rs.getDate("use_date"),
                rs.getLong("model_id"),
                rs.getInt("use_count"),
                rs.getInt("wrong_feedback_count"),
                rs.getInt("right_feedback_count")
        );
    }

    //将java.util.Date转换为java.sql.Date。当向sql语句传入Date变量时，必须先使用这个函数转换Date的类型。
    private java.sql.Date dateConvert(Date date){
        return new java.sql.Date(date.getTime());
    }

    @Override
    public void recordOneUse(Date use_date, long model_id) {
        //从数据库中取出数据项
        UsageItem usage;
        try {
            usage = jdbc.queryForObject("SELECT * FROM usages WHERE use_date = ? AND model_id = ?",
                    this::usageItemMapper,
                    dateConvert(use_date),
                    model_id);
        }
        catch(EmptyResultDataAccessException e) {
            //如果未取到则新建
            usage = insertUsageRecord(use_date, model_id);
        }

        //更新数据项
        int use_count = usage.getUse_count() + 1;

        //写回数据库
        int res = jdbc.update("UPDATE usages SET use_count = ? WHERE use_date = ? AND model_id = ?",
                use_count,
                dateConvert(use_date),
                model_id);

        if(res != 1){
            throw new RuntimeException("我也不知道怎么了，本来不应该执行到这里的！12445342");
        }
    }

    @Override
    public void recordOneRightFeedback(Date use_date, long model_id) {
        //从数据库中取出数据项
        UsageItem usage;
        try {
            usage = jdbc.queryForObject("SELECT * FROM usages WHERE use_date = ? AND model_id = ?",
                    this::usageItemMapper,
                    dateConvert(use_date),
                    model_id);
        }
        catch(EmptyResultDataAccessException e) {
            //如果未取到则新建
            usage = insertUsageRecord(use_date, model_id);
        }

        //更新数据项
        int right_feedback_count = usage.getRight_feedback_count() + 1;

        //写回数据库
        int res = jdbc.update("UPDATE usages SET right_feedback_count = ? WHERE use_date = ? AND model_id = ?",
                right_feedback_count,
                dateConvert(use_date),
                model_id);

        if(res != 1){
            throw new RuntimeException("我也不知道怎么了，本来不应该执行到这里的！83647282");
        }
    }

    @Override
    public void recordOneWrongFeedback(Date use_date, long model_id) {
        //从数据库中取出数据项
        UsageItem usage;
        try {
            usage = jdbc.queryForObject("SELECT * FROM usages WHERE use_date = ? AND model_id = ?",
                    this::usageItemMapper,
                    dateConvert(use_date),
                    model_id);
        }
        catch(EmptyResultDataAccessException e) {
            //如果未取到则新建
            usage = insertUsageRecord(use_date, model_id);
        }

        //更新数据项
        int wrong_feedback_count = usage.getWrong_feedback_count() + 1;

        //写回数据库
        int res = jdbc.update("UPDATE usages SET wrong_feedback_count = ? WHERE use_date = ? AND model_id = ?",
                wrong_feedback_count,
                dateConvert(use_date),
                model_id);

        if(res != 1){
            throw new RuntimeException("我也不知道怎么了，本来不应该执行到这里的！09786844");
        }
    }
}
