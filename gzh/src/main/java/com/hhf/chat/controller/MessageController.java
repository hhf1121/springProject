package com.hhf.chat.controller;

import com.hhf.chat.util.SHA1;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 云路供应链科技有限公司 版权所有 © Copyright 2020
 *
 * @author hehongfei
 * @Description:
 * @date 2021-08-25
 */

@Slf4j
@RestController
@RequestMapping("/wx")
public class MessageController {


    //微信公众号基本配置中的token
    @Value("${wx.token:mytoken}")
    private String token;

    @Autowired
    private WxMpService wxMpService;

    @Autowired
    private WxMpMessageRouter messageRouter;


    @GetMapping("/openid")
    public String openid(@RequestParam("code")String code,@RequestParam("state")String returnUrl){
        log.info("code:{},returnUrl:{}",code,returnUrl);
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken=new WxMpOAuth2AccessToken();
        try {
            wxMpOAuth2AccessToken=wxMpService.oauth2getAccessToken(code);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        String openid=wxMpOAuth2AccessToken.getOpenId();
        return "redirect:"+returnUrl+"?openid="+openid;
    }


    @GetMapping("/authorize")
    public String authorize(@RequestParam("returnUrl") String returnUrl){
        String url="http://xf7ax7.natappfree.cc/wx/openid";
        String encode=null;
        try{
            encode = URLEncoder.encode(returnUrl, "utf-8");
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        String redirectUrl=wxMpService.oauth2buildAuthorizationUrl(url, WxConsts.OAuth2Scope.SNSAPI_BASE,encode);
        return "redirect:"+redirectUrl;
    }


    /**
     * 公众号->设置与开发->基本配置->服务器配置(已启用)
     * @param request
     * @return
     */
    @RequestMapping(value = "/signature",method = RequestMethod.GET)
    public String signature(HttpServletRequest request, HttpServletResponse response){
        log.info("公众号验证请求...");
        String signature=request.getParameter("signature");
        String timestamp=request.getParameter("timestamp");
        String nonce=request.getParameter("nonce");
        String echostr=request.getParameter("echostr");
        log.info("加密入参：timestamp:{},nonce:{},signature:{},echostr:{}",timestamp,nonce,signature,echostr);
        //这里是对三个参数进行加密
        String jiami = SHA1.getSHA1(token, timestamp, nonce,"");
        log.info("解密之后：{}",jiami);
        //第一次校验,仅在认证微信开发者模式时调用一次即可
        if(echostr!=null && jiami.equals(signature)){
            log.info("申请验证成功");
            return  echostr;
        }
        return null;
    }


    //接收+回复
    @RequestMapping(value = "/signature",method = RequestMethod.POST)
    public String call(@RequestBody String requestBody,HttpServletRequest request) throws IOException {
        log.info("requestId={},接收到WX推送消息");
//        Map<String, String> stringStringMap = null;
        WxMpXmlMessage inMessage = null;
        try {
            inMessage = WxMpXmlMessage.fromXml(requestBody);
        } catch (Exception e) {
            log.info("requestId={},xml文件解析失败",e);
        }
        WxMpXmlOutMessage wxMpXmlOutMessage = messageRouter.route(inMessage);
        if(wxMpXmlOutMessage==null){
            return "";
        }
        String out = wxMpXmlOutMessage.toXml();
        log.info("requestId={}，返回xml信息结果{}",out);
        return out;

//        log.info("公众号处理消息start...");
//        response.setContentType("text/html;charset=UTF-8");
//        PrintWriter pw = response.getWriter();
//        String wxMsgXml = IOUtils.toString(request.getInputStream(), "utf-8");
//        log.info("获取的数据信息>>>>>"+wxMsgXml);
       /* <xml>
            <ToUserName><![CDATA[gh_5c1cb964c3e4]]></ToUserName>
            <FromUserName><![CDATA[o2cMk6ukfk0Sr2m21d4s-1EHYOWA]]></FromUserName>
            <CreateTime>1629879382</CreateTime>
            <MsgType><![CDATA[text]]></MsgType>
            <Content><![CDATA[1]]></Content>
            <MsgId>23334263494812302</MsgId>
        </xml>*/
//        String returnXml ="<xml>\n" +
//                "            <ToUserName><![CDATA[o2cMk6ukfk0Sr2m21d4s-1EHYOWA]]></ToUserName>\n" +
//                "            <FromUserName><![CDATA[gh_5c1cb964c3e4]]></FromUserName>\n" +
//                "            <CreateTime>1629879382</CreateTime>\n" +
//                "            <MsgType><![CDATA[text]]></MsgType>\n" +
//                "            <Content><![CDATA[hello！]]></Content>\n" +
//                "            <MsgId>23334263494812302</MsgId>\n" +
//                "        </xml>";
//        log.info("wxMsgXml>>>>>>>>>>>>>>"+wxMsgXml);
//        log.info("returnXml>>>>>>>>>>>>>>"+returnXml);
//        pw.println(returnXml);
//        return null;
    }

}
