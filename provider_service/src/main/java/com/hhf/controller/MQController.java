package com.hhf.controller;


import com.hhf.entity.User;
import com.hhf.rocketMq.SpringProducer;
import com.hhf.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("rocketmq")
public class MQController {


    @Autowired
    private SpringProducer springProducer;

    /**
     * 发送mq
     * @param topic
     * @param name
     * @param password
     * @return
     */
    @GetMapping("/sendMessage")
    public Map<String,Object> sendMessage(String topic,String name,String password){
        User user=new User();
        user.setName(name);
        user.setPassWord(password);
        springProducer.sendMessage(topic,user);
        return ResultUtils.getSuccessResult("发送成功");
    }

    /**
     * 发送顺序消息，以userId为顺序消费的键
     * @param topic
     * @return
     */
    @GetMapping("/sendMessageInSort")
    public Map<String,Object> sendMessageInSort(String topic){
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 5; j++) {
                User user=new User(i,"名字"+j);
                springProducer.sendMessageInSort(topic,user);
            }
        }
        return ResultUtils.getSuccessResult("发送成功");
    }


    /**
     * 发送顺序消息，以userId为顺序消费的键
     * @param topic
     * @return
     */
    @GetMapping("/sendMessageInTransaction")
    public Map<String,Object> sendMessageInTransaction(String topic,String name,String password){
        User user=new User();
        user.setName(name);
        user.setPassWord(password);
        springProducer.sendMessageInTransaction(topic,user);
        return ResultUtils.getSuccessResult("发送成功");
    }

}
