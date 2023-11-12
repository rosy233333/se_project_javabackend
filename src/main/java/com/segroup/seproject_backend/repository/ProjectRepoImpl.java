package com.segroup.seproject_backend.repository;

import com.segroup.seproject_backend.data_item.ImageDBItem;
import com.segroup.seproject_backend.data_item.ModelDBItem;
import com.segroup.seproject_backend.data_item.UsageDBItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

//使用这个类来操作数据库。
//使用方法：将功能的执行过程中，所有对数据库的操作封装成这个类的一个方法（例如RecordOneUse，在数据库中记录一次使用）。
//然后在Controller类的功能代码中调用这个方法。
//注意：新增方法时，需要在接口ProjectRepo中新增方法，再在这个类中实现接口中新增的方法。
//提示：常使用JdbcTemplate的两个方法操作数据库，JdbcTemplate::update、JdbcTemplate::queryForObject和JdbcTemplate::queryForList。
//这些方法的使用都可以参考这个类原有的代码。
@Repository
public class ProjectRepoImpl implements ProjectRepo{

    @Autowired
    private JdbcTemplate jdbc;

    //使用数据库的Date类型时，需要注意：
    //Date有两种：java.util.Date和java.sql.Date。
    //Java代码里经常使用的是java.util.Date，查询数据库得到的/向数据库中插入的是java.sql.Date。
    //java.sql.Date继承自java.util.Date，因此可以直接把java.sql.Date赋值给java.util.Date。
    //但反过来就不可以。所以需要使用下面这个函数，将java.util.Date转换为java.sql.Date。
    //当向sql语句传入java.util.Date变量时，必须先使用这个函数转换Date的类型。
    //另外说一句：希望对Date的转换能够全部局限到这个类的内部，这个类对外提供的方法全部使用java.util.Date，因此在这个类之外，无需考虑Date类型转换的问题。
    private java.sql.Date dateConvert(Date date){
        return new java.sql.Date(date.getTime());
    }

    //为使用频次表添加记录项
    private UsageDBItem insertUsageRecord(Date use_date, long model_id) {
        jdbc.update("INSERT INTO usages(use_date, model_id) VALUES (?, ?)", dateConvert(use_date), model_id);
        return new UsageDBItem(use_date, model_id, 0, 0, 0);
    }

    //记录一次使用
    @Override
    public void recordOneUse(Date use_date, long model_id) {
        //从数据库中取出数据项
        UsageDBItem usage;
        try {
            usage = jdbc.queryForObject("SELECT * FROM usages WHERE use_date = ? AND model_id = ?",
                    new BeanPropertyRowMapper<>(UsageDBItem.class),
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

    //记录一次“正确”反馈
    @Override
    public void recordOneRightFeedback(Date use_date, long model_id) {
        //从数据库中取出数据项
        UsageDBItem usage;
        try {
            usage = jdbc.queryForObject("SELECT * FROM usages WHERE use_date = ? AND model_id = ?",
                    new BeanPropertyRowMapper<>(UsageDBItem.class),
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

    //记录一次“错误”反馈
    @Override
    public void recordOneWrongFeedback(Date use_date, long model_id) {
        //从数据库中取出数据项
        UsageDBItem usage;
        try {
            usage = jdbc.queryForObject("SELECT * FROM usages WHERE use_date = ? AND model_id = ?",
                    new BeanPropertyRowMapper<>(UsageDBItem.class),
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

    // 根据数据集id查询该数据集的图片
    public List<ImageDBItem> findImagesByDatasetId(long dataset_id) {
        return jdbc.queryForList(
            """
                SELECT * FROM images WHERE image_id IN
                    SELECT image_id FROM dataset_image WHERE dataset_id = ? ;
            """,
            ImageDBItem.class,
            dataset_id
        );
    }

    // 向模型表中插入模型
    // 不会插入模型id，因为id由数据库自动分配。
    // 不会插入启用日期，因为新建的模型还未启用。
    public void insertModel(ModelDBItem model) {
        jdbc.update("INSERT INTO models(user_id, model_name, model_path, dataset_id, train_accuracy, is_active, model_create_date) VALUES (?, ?, ?, ?, ?, ?, ?)",
            model.getUser_id(),
            model.getModel_name(),
            model.getModel_path(),
            model.getDataset_id(),
            model.getTrain_accuracy(),
            model.getIs_active(),
            dateConvert(model.getModel_create_date())
        );
    }
}
