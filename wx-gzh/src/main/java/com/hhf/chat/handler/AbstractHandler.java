package com.hhf.chat.handler;

import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.kefu.WxMpKefuMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import org.springframework.beans.factory.annotation.Value;

/**
 *
 * @author hehongfei
 * @Description:
 * @date 2021-08-27
 */

@Slf4j
public abstract class AbstractHandler implements WxMpMessageHandler {

    @Value("${wx.redirectUrl:http://sgva8d.natappfree.cc}")
    private String redirectUrl;


    public void sendMessage(WxMpXmlMessage wxMpXmlMessage, WxMpService wxMpService, String openId) {
        WxMpKefuMessage wxMpKefuMessage = null;
        if("event".equals(wxMpXmlMessage.getMsgType())){
            wxMpKefuMessage = WxMpKefuMessage.TEXT().content(wxMpXmlMessage.getContent()).build();
        }
        try {
            wxMpKefuMessage.setToUser(openId);
            wxMpService.getKefuService().sendKefuMessage(wxMpKefuMessage);
        } catch (WxErrorException e) {
            log.warn("公众号回复失败",e);
        }
    }


    public void sendAndLogin(WxMpXmlMessage wxMpXmlMessage, WxMpService wxMpService, String openId) {
        //生成code
        String code = wxMpService.oauth2buildAuthorizationUrl(redirectUrl, "snsapi_userinfo", "my_hhf");

        wxMpXmlMessage.setContent("<a href=\""+code+"\">确定登录</a>");
        wxMpXmlMessage.setUrl(code);
        WxMpKefuMessage wxMpKefuMessage = null;
        if("event".equals(wxMpXmlMessage.getMsgType())){
            wxMpKefuMessage = WxMpKefuMessage.TEXT().content(wxMpXmlMessage.getContent()).build();
        }
        try {
            wxMpKefuMessage.setToUser(openId);
            wxMpService.getKefuService().sendKefuMessage(wxMpKefuMessage);
        } catch (WxErrorException e) {
            log.warn("公众号自动登录失败",e);
        }
    }


}
