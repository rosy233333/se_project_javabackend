package com.segroup.seproject_backend.controller;

import com.segroup.seproject_backend.data_item.FeedbackItem;
import com.segroup.seproject_backend.repository.ProjectRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@Controller
public class FeedbackController {
    @Autowired
    private ProjectRepo projectRepo;

    @PostMapping("/use/feedback")
    @ResponseBody
    public String handleFeedback(FeedbackItem feedbackItem) {
        Date date = new Date();

        if(feedbackItem.getFeedback().equals("right")) {
            projectRepo.recordOneRightFeedback(date, 0); //之后需要修改-获取模型id
            System.out.println("用户进行了反馈，值为right");
            return "valid feedback";
        }
        else if(feedbackItem.getFeedback().equals("wrong")) {
            projectRepo.recordOneWrongFeedback(date, 0); //之后需要修改-获取模型id
            System.out.println("用户进行了反馈，值为wrong");
            return "valid feedback";
        }
        else {
            return "invalid feedback";
        }
    }
}
