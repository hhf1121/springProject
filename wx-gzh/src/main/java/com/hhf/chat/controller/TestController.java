package com.hhf.chat.controller;

import com.hhf.chat.entity.User;
import com.hhf.chat.feign.UserFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 云路供应链科技有限公司 版权所有 © Copyright 2020
 *
 * @author hehongfei
 * @Description:
 * @date 2021-08-24
 */

@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {

    @Autowired
    private UserFeign userFeign;


    @RequestMapping("/test")
    public String test(){
        log.info("test");
        userFeign.registUser(new User());
        return "success";
    }

}
