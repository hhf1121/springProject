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
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 *
 * @author hehongfei
 * @Description:
 * @date 2021-08-25
 */

@Slf4j
@Component
public class MsgHandler implements WxMpMessageHandler {

    @Value("${wx.msg.mytemplate}")
    private String mytemplate;

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
        if(wxMpXmlMessage.getContent().equals("模板")){
            //实例化模板对象
            WxMpTemplateMessage wxMpTemplateMessage = new WxMpTemplateMessage();
            //设置模板ID
            wxMpTemplateMessage.setTemplateId(mytemplate);
            //设置发送给哪个用户
            wxMpTemplateMessage.setToUser(wxMpXmlMessage.getFromUser());
            wxMpTemplateMessage.setUrl("http://www.baidu.com");
            //构建消息格式
            List<WxMpTemplateData> listData = Arrays.asList(
                    new WxMpTemplateData("title", "恭喜你支付成功！", "#173177"),
                    new WxMpTemplateData("name", "贺.", "#173177"),
                    new WxMpTemplateData("age", "21", "#173177"),
                    new WxMpTemplateData("remark", "如有疑问，请联系客服电话：021-54145323", "#173177")
            );
            //接收发送模板消息结果,就是msgid，if(msgid! = null)即成功
            String wxTemplateResult = null;
            //放进模板对象。准备发送
            wxMpTemplateMessage.setData(listData);
            try {
                //发送模板
                wxTemplateResult = wxMpService.getTemplateMsgService().sendTemplateMsg(wxMpTemplateMessage);
                log.info(JSON.toJSONString(wxTemplateResult));
                return null;
            } catch (WxErrorException e) {
                log.error("发送模板消息异常：{}", e.getMessage());
                e.printStackTrace();
                return null;
            }
        }
        return xmlOutTextMessage;
    }
}
