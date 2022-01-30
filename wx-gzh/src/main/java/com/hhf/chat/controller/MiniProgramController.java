package com.hhf.chat.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.config.impl.WxMaDefaultConfigImpl;
import com.alibaba.fastjson.JSON;
import com.hhf.chat.config.WxMnConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author hehongfei
 * @Description:
 * @date 2021-12-02
 */
@RestController
@RequestMapping("/mini/")
@Slf4j
public class MiniProgramController {


    @Autowired
    private WxMnConfig wxMnConfig;

    @RequestMapping("/hello")
    public String hello(){
        log.info("hello");
        return "hello";
    }


    @GetMapping("/login")
    public String login(@RequestParam("code") String code){
        log.info("code:===>{}",code);
        StopWatch stopWatch = new StopWatch();
        String appId = wxMnConfig.getAppId();
        final WxMaService wxService = new WxMaServiceImpl();
        WxMaDefaultConfigImpl config = new WxMaDefaultConfigImpl();
        config.setAppid(wxMnConfig.getAppId());
        config.setSecret(wxMnConfig.getAppSecret());
        config.setToken(wxMnConfig.getToken());
        wxService.setWxMaConfig(config);;
        WxMaJscode2SessionResult session = null;
        try {
            stopWatch.start("调用微信");
            session = wxService.getUserService().getSessionInfo(code);
            stopWatch.stop();
        } catch (Exception e) {
            log.error("登录失败：{}", e);
        }
        System.out.println(JSON.toJSONString(session));
        return "success";
    }

}
