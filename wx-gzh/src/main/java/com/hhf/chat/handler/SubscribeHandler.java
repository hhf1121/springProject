package com.hhf.chat.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.hhf.chat.entity.User;
import com.hhf.chat.feign.UserFeign;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author Binary Wang(https://github.com/binarywang)
 */
@Component
@Slf4j
public class SubscribeHandler extends AbstractHandler {

    private static String content="终于等到你~";


    @Value("${wx.msg.registtemplate}")
    public String registtemplate="终于等到你~";

    @Value("${wx.projectUrl}")
    public String projectUrl;


    @Autowired
    private UserFeign userFeign;


    /**
     * 关注之后，发送账号密码
     * @param wxMessage
     * @param context
     * @param weixinService
     * @param sessionManager
     * @return
     * @throws WxErrorException
     */
    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService weixinService,
                                    WxSessionManager sessionManager) throws WxErrorException {
        String requestId = UUID.randomUUID().toString().replaceAll("-", "");
        log.info("requestId=>{},新关注用户 OPENID:{} " ,requestId,wxMessage.getFromUser());
        // 获取微信用户基本信息
        WxMpUser userWxInfo =null;
        try {
             userWxInfo = weixinService.getUserService().userInfo(wxMessage.getFromUser(), null);
            log.info(JSON.toJSONString(userWxInfo));
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        WxMpXmlOutMessage xmlOutTextMessage=WxMpXmlOutMessage.TEXT()
                .content(content)
                .fromUser(wxMessage.getToUser())
                .toUser(wxMessage.getFromUser())
                .build();

        log.info(JSON.toJSONString(userWxInfo));
        try{
            // 生成账号
            User user=new User();
            user.setPassWord(UUID.randomUUID().toString().replaceAll("-",""));
            user.setOpenId(userWxInfo.getOpenId());
//            user.setUserName(userWxInfo.getNickname());
            user.setName(userWxInfo.getNickname());
            user.setAddress(userWxInfo.getCountry()+"-"+userWxInfo.getProvince()+"-"+userWxInfo.getCity());
            user.setUserName(userWxInfo.getSubscribeTime()+userWxInfo.getNickname().hashCode()+"");
            user.setPicPath(userWxInfo.getHeadImgUrl());
            Map<String, Object> map = userFeign.registUser(user);
            Boolean o = Boolean.valueOf(map.get("success")+"");
            if(o){//注册了账号，发送模板消息
                User registUser = JSON.parseObject(JSON.toJSONString(map.get("data")),User.class) ;
                //模板消息
                List<WxMpTemplateData> listData = Lists.newArrayList(
                        new WxMpTemplateData("username", registUser.getUserName(),"red"),
                        new WxMpTemplateData("password", registUser.getPassWord(),"red"),
                        new WxMpTemplateData("url", "http://learn.hhf.com","blue")
                );
                sendTemplateMsg(registtemplate,projectUrl+"/jsp/getUserInfo?openId="+registUser.getOpenId(),listData,wxMessage,weixinService);
            }else {
                //存在账号，直接登录
                sendAndLogin(wxMessage,weixinService,wxMessage.getFromUser());
//                throw new Exception(map.get("errorMsg")+"");
            }
        }catch (Exception w){
            w.printStackTrace();
            log.error(w.getMessage());
        }

        //发送问候消息
        return xmlOutTextMessage;
    }


    private void sendTemplateMsg(String templateId,String url,List<WxMpTemplateData> listData,WxMpXmlMessage wxMpXmlMessage,WxMpService wxMpService){
        //实例化模板对象
        WxMpTemplateMessage wxMpTemplateMessage = new WxMpTemplateMessage();
        //设置模板ID
        wxMpTemplateMessage.setTemplateId(templateId);
        //设置发送给哪个用户
        wxMpTemplateMessage.setToUser(wxMpXmlMessage.getFromUser());
        wxMpTemplateMessage.setUrl(url);
        //构建消息格式
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
