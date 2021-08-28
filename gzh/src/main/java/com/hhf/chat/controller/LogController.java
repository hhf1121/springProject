package com.hhf.chat.controller;


import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.hhf.chat.util.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/wx")
@Slf4j
public class LogController {

    @Autowired
    private WxMpService wxMpService;

    @Value("${wx.redirectUrl:http://sgva8d.natappfree.cc}")
    private String redirectUrl;

    @Value("${wx.appId}")
    private String appId;


    @Value("${wx.appSecret}")
    private String appSecret;


    /**
     * 临时二维码的ticket
     * 关注当前公众号
     * @return
     */
    @RequestMapping("/getTicket")
    public String getTicket(){
        String param="{\"expire_seconds\": 604800, \"action_name\": \"QR_STR_SCENE\", \"action_info\": {\"scene\": {\"scene_str\": \"test\"}}}";
        WxMpQrCodeTicket wxMpQrCodeTicket=null;
        try {
             wxMpQrCodeTicket = wxMpService.getQrcodeService().qrCodeCreateTmpTicket(param,720);
            log.info(JSON.toJSONString(wxMpQrCodeTicket));
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        String ticket = wxMpQrCodeTicket.getTicket();
        String erweima = "";
        try {
             erweima = wxMpService.getQrcodeService().qrCodePictureUrl(ticket);
             log.info(erweima);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        return erweima;
    }


    /**
     * 二维码 登录code
     * @return
     */
    @RequestMapping("/getCode")
    public String authorize(){
        String code = wxMpService.oauth2buildAuthorizationUrl(redirectUrl, "snsapi_userinfo", "my_hhf");
        log.info(code);
        return code;
    }


    /**
     * 微信回调接口
     * @param code
     * @param state
     * @return
     */
    @RequestMapping("/loginCallback")
    public Map<String,Object> loginCallback(@RequestParam("code")String code,@RequestParam("state")String state){
        log.info("code:{},state:{}",code,state);
        WxMpUser zh_cn = null;
        try {
            WxMpOAuth2AccessToken wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
            log.info(JSON.toJSONString(wxMpOAuth2AccessToken));
            zh_cn = wxMpService.oauth2getUserInfo(wxMpOAuth2AccessToken, "zh_CN");
            log.info("用户信息：{}",JSON.toJSONString(zh_cn));
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        return ResultUtils.getSuccessResult(zh_cn);
    }


    @RequestMapping("/getAppinfo")
    public Map<String,Object> getAppinfo(){
        log.info("getAppinfo...");
        Map<String,Object> map= Maps.newHashMap();
        map.put("appId",appId);
        map.put("appSecret",appSecret);
        return map;
    }


    }
