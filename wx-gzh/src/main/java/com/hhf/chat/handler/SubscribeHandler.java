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
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

/**
 * @author Binary Wang(https://github.com/binarywang)
 */
@Component
@Slf4j
public class SubscribeHandler implements WxMpMessageHandler {

    private static String content="终于等到你~";

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService weixinService,
                                    WxSessionManager sessionManager) throws WxErrorException {
        String requestId = UUID.randomUUID().toString().replaceAll("-", "");
        log.info("requestId=>{},新关注用户 OPENID:{} " ,requestId,wxMessage.getFromUser());
        // 获取微信用户基本信息
        try {
            WxMpUser userWxInfo = weixinService.getUserService().userInfo(wxMessage.getFromUser(), null);
            log.info(JSON.toJSONString(userWxInfo));
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        WxMpXmlOutMessage xmlOutTextMessage=WxMpXmlOutMessage.TEXT()
                .content(content)
                .fromUser(wxMessage.getToUser())
                .toUser(wxMessage.getFromUser())
                .build();
//        WxMpKefuMessage build = WxMpKefuMessage.TEXT().content(content).build();
//        build.setToUser(wxMessage.getFromUser());
//        weixinService.getKefuService().sendKefuMessage(build);
        return xmlOutTextMessage;
    }

}
