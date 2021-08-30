package com.hhf.chat.handler;

import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.kefu.WxMpKefuMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;

/**
 * 云路供应链科技有限公司 版权所有 © Copyright 2020
 *
 * @author hehongfei
 * @Description:
 * @date 2021-08-27
 */

@Slf4j
public abstract class AbstractHandler implements WxMpMessageHandler {

    public void sendMessage(WxMpXmlMessage wxMpXmlMessage, WxMpService wxMpService, String openId) {
        WxMpKefuMessage wxMpKefuMessage = null;
        if("event".equals(wxMpXmlMessage.getMsgType())){
            wxMpKefuMessage = WxMpKefuMessage.TEXT().content(wxMpXmlMessage.getContent()).build();
        }
        try {
            wxMpKefuMessage.setToUser(openId);
            wxMpService.getKefuService().sendKefuMessage(wxMpKefuMessage);
        } catch (WxErrorException e) {
            log.warn("微信客户自动回复失败",e);
        }
    }


}
