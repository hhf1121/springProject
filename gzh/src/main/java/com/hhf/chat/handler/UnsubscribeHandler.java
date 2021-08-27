package com.hhf.chat.handler;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

/**
 * @author Binary Wang(https://github.com/binarywang)
 */
@Slf4j
@Component
public class UnsubscribeHandler implements WxMpMessageHandler {


    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService wxMpService,
                                    WxSessionManager sessionManager) {
        String requestId = UUID.randomUUID().toString().replaceAll("-", "");
        String openId = wxMessage.getFromUser();
        log.info("requestId=>{},取消关注用户 OPENID:{} " ,requestId, openId);
        WxMpUser wxMpUser = null;
        try {
            wxMpUser = wxMpService.getUserService().userInfo(openId);
            log.info(JSON.toJSONString(wxMpUser));
        } catch (WxErrorException e) {
            log.warn("requestId=>{},调用微信接口获取用户信息失败：openId:{}",requestId,openId,e);
        }

        if(wxMpUser==null){
            log.info("requestId=>{},获取用户信息失败：{}",requestId,openId);
            return null;
        }
        return null;
    }

}
