package com.hhf.chat.controller;

import com.hhf.chat.entity.User;
import com.hhf.chat.feign.UserFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 云路供应链科技有限公司 版权所有 © Copyright 2020
 *
 * @author hehongfei
 * @Description:
 * @date 2021-09-01
 */

@RequestMapping("/jsp")
@Controller
@Slf4j
public class JSPController {


    @Autowired
    private UserFeign userFeign;

    @RequestMapping("/getUserInfo")
    public ModelAndView getUserInfo(String openId){
        log.info("getUserInfo,入参：{}",openId);
        User user = userFeign.getUserByOpenid(openId);
        ModelAndView mv = new ModelAndView();
        mv.setViewName("userInfo");
        mv.addObject("user", user);
        return mv;
    }
}
