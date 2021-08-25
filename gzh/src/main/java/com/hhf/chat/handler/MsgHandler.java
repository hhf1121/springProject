package com.hhf.chat.handler;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.kefu.WxMpKefuMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutTextMessage;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 云路供应链科技有限公司 版权所有 © Copyright 2020
 *
 * @author hehongfei
 * @Description:
 * @date 2021-08-25
 */

@Slf4j
@Component
public class MsgHandler implements WxMpMessageHandler {
    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMpXmlMessage, Map<String, Object> map, WxMpService wxMpService, WxSessionManager wxSessionManager) throws WxErrorException {
        log.info("用户发送消息:{}", JSON.toJSONString(wxMpXmlMessage));
//        WxMpKefuMessage wxMpKefuMessage = WxMpKefuMessage.TEXT().content(wxMpXmlMessage.getContent()).build();
        WxMpXmlOutMessage xmlOutTextMessage=null;
        if("text".equals(wxMpXmlMessage.getMsgType())){
            xmlOutTextMessage = WxMpXmlOutMessage.TEXT().content(wxMpXmlMessage.getContent()).fromUser(wxMpXmlMessage.getToUser()).toUser(wxMpXmlMessage.getFromUser()).build();
        }else if("image".equals(wxMpXmlMessage.getMsgType())){
            xmlOutTextMessage = WxMpXmlOutMessage.IMAGE().mediaId(wxMpXmlMessage.getMediaId()).fromUser(wxMpXmlMessage.getToUser()).toUser(wxMpXmlMessage.getFromUser()).build();
        }else if("voice".equals(wxMpXmlMessage.getMsgType())){
            xmlOutTextMessage = WxMpXmlOutMessage.VOICE().mediaId(wxMpXmlMessage.getMediaId()).fromUser(wxMpXmlMessage.getToUser()).toUser(wxMpXmlMessage.getFromUser()).build();
        }else if("video".equals(wxMpXmlMessage.getMsgType())){
            xmlOutTextMessage = WxMpXmlOutMessage.VIDEO().mediaId(wxMpXmlMessage.getMediaId()).fromUser(wxMpXmlMessage.getToUser()).toUser(wxMpXmlMessage.getFromUser()).build();
        }else if("news".equals(wxMpXmlMessage.getMsgType())){
            xmlOutTextMessage = WxMpXmlOutMessage.NEWS().addArticle().fromUser(wxMpXmlMessage.getToUser()).toUser(wxMpXmlMessage.getFromUser()).build();
        }else{
            log.warn("requestId=>{},暂不支持回复类型：{}");
            return null;
        }
//        try {
//            String fromUser = wxMpXmlMessage.getFromUser();
//            log.info("fromUser:{}",fromUser);
//            wxMpKefuMessage.setToUser(fromUser);
//            wxMpService.getKefuService().sendKefuMessage(wxMpKefuMessage);
//        } catch (WxErrorException e) {
//            log.warn("微信客户自动回复失败",e);
//        }
        return xmlOutTextMessage;
    }
}
