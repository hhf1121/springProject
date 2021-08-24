package com.hhf.chat.controller;

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
public class TestController {


    @RequestMapping("/test")
    public String test(){
        return "success";
    }

}
