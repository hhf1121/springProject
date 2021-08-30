package com.hhf.chat.handler;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 云路供应链科技有限公司 版权所有 © Copyright 2020
 *
 * @author hehongfei
 * @Description:
 * @date 2021-08-27
 */

@Component
@Slf4j
public class MenuHandler extends AbstractHandler {

//https://m701.music.126.net/20210827160106/42596885d7ab53f41e3017166c9550ed/jdyyaac/obj/w5rDlsOJwrLDjj7CmsOj/10398401966/2385/f06e/991c/3c7d7fd56b7f71a04b2c32ac6149a93c.m4a
    @Value("${wx.msg.musictemplate}")
    public String musictemplate;


    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMpXmlMessage, Map<String, Object> map, WxMpService wxMpService, WxSessionManager wxSessionManager) throws WxErrorException {
        if (WxConsts.EventType.VIEW.equals(wxMpXmlMessage.getEvent())) {
            return null;
        }
        if(wxMpXmlMessage.getEventKey().equals("good")){
            log.info("被"+wxMpXmlMessage.getFromUser()+"赞了一下！");
            return  WxMpXmlOutMessage.TEXT().content("谢谢赞~")
                    .fromUser(wxMpXmlMessage.getToUser()).toUser(wxMpXmlMessage.getFromUser())
                    .build();

        }
        if(wxMpXmlMessage.getEventKey().equals("music")){
            log.info("今日歌曲");
            List<String> musics= Lists.newArrayList("1.冬天的秘密","2.起风了","3.浪花一朵朵");
            musics.forEach(o-> {
                wxMpXmlMessage.setContent(o);
                this.sendMessage(wxMpXmlMessage, wxMpService, wxMpXmlMessage.getFromUser());
            });
        }
        if(wxMpXmlMessage.getEventKey().equals("music-online")){
            List<String> musics= Lists.newArrayList("冬天的秘密","起风了","浪花一朵朵");
            List<String> musicsUrl= Lists.newArrayList(
                    "https://m701.music.126.net/20210827160106/42596885d7ab53f41e3017166c9550ed/jdyyaac/obj/w5rDlsOJwrLDjj7CmsOj/10398401966/2385/f06e/991c/3c7d7fd56b7f71a04b2c32ac6149a93c.m4a",
                    "https://m801.music.126.net/20210827160319/10ed324572f000845853aa46afc127d0/jdyyaac/565b/065f/0358/a1cd0e25a815dffcc0c1422398efde9e.m4a",
                    "https://m701.music.126.net/20210827160338/7b457d9c1bbc45af7e08915f33f811a1/jdyyaac/0659/545f/045e/13d2d7d209c5edf03a4634c869f71376.m4a");

            for (int i = 0; i < musics.size(); i++) {
                //实例化模板对象
                WxMpTemplateMessage wxMpTemplateMessage = new WxMpTemplateMessage();
                //设置模板ID
                wxMpTemplateMessage.setTemplateId(musictemplate);
                //设置发送给哪个用户
                wxMpTemplateMessage.setToUser(wxMpXmlMessage.getFromUser());
                wxMpTemplateMessage.setUrl(musicsUrl.get(i));
                //构建消息格式
                List<WxMpTemplateData> listData = Arrays.asList(new WxMpTemplateData("name", musics.get(i)));
                String wxTemplateResult = null;
                //放进模板对象。准备发送
                wxMpTemplateMessage.setData(listData);
                try {
                    //发送模板
                    wxTemplateResult = wxMpService.getTemplateMsgService().sendTemplateMsg(wxMpTemplateMessage);
                    log.info(JSON.toJSONString(wxTemplateResult));
                } catch (WxErrorException e) {
                    log.error("发送模板消息异常：{}", e.getMessage());
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
