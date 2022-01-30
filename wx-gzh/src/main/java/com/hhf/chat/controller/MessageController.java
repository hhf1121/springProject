package com.hhf.chat.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hhf.chat.dto.WxMaterialDto;
import com.hhf.chat.entity.User;
import com.hhf.chat.service.UserService;
import com.hhf.chat.util.ResultUtils;
import com.hhf.chat.util.SHA1;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxError;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.material.WxMpMaterialFileBatchGetResult;
import me.chanjar.weixin.mp.bean.material.WxMpMaterialNewsBatchGetResult;
import me.chanjar.weixin.mp.bean.menu.WxMpMenu;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
@RestController
@RequestMapping("/wx")
public class MessageController implements InitializingBean {


    //微信公众号基本配置中的token
    @Value("${wx.token:mytoken}")
    private String token;

    @Autowired
    private WxMpService wxMpService;

    @Autowired
    private WxMpMessageRouter messageRouter;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UserService userService;

    @Value("${wx.msg.logintemplate}")
    private String loginTemplate;

    private String TOKEN;


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
        if(StringUtils.isEmpty(echostr)||StringUtils.isEmpty(signature)||StringUtils.isEmpty(timestamp)||StringUtils.isEmpty(nonce)){
            log.warn("校验参数不全！");
            return "校验参数不全";
        }
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

    /**
     * 获取token
     * @return
     */
    @GetMapping("/getToken")
    public String getToken() throws WxErrorException {
        TOKEN = wxMpService.getAccessToken();
        return TOKEN;
    }

    /**
     *  创建菜单
     * @param menuJson
     * @return
     */
    @PostMapping("/createMenu")
    public Map<String,Object> createMenu(@RequestBody String menuJson){
        String s = "";
        try {
            s = wxMpService.getMenuService().menuCreate(menuJson);
        } catch (WxErrorException e) {
            log.error("创建菜单失败");
            e.printStackTrace();
        }finally {
            try {
                WxMpMenu wxMpMenu = wxMpService.getMenuService().menuGet();
                log.info(JSON.toJSONString(wxMpMenu));
            } catch (WxErrorException e) {
                e.printStackTrace();
            }
            return ResultUtils.getSuccessResult(s);
        }
    }


    /**
     * 设置行业
     * @param body
     * @return
     */
    @PostMapping("/setIndustry")
    public Map<String,Object> setIndustry(@RequestBody String body) throws WxErrorException {
        String s = "";
        String url="https://api.weixin.qq.com/cgi-bin/template/api_set_industry?access_token="+wxMpService.getAccessToken();
        try {
            ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(url, body, String.class);
        } catch (Exception e) {
            log.error("模板消息-设置行业");
            e.printStackTrace();
        }finally {
            return ResultUtils.getSuccessResult(s);
        }
    }



    /**
     * 获取行业
     * @return
     */
    @GetMapping("/getIndustry")
    public Map<String,Object> getIndustry() throws WxErrorException {
        String s = "";
        String url="https://api.weixin.qq.com/cgi-bin/template/get_industry?access_token="+wxMpService.getAccessToken();
        try {
            ResponseEntity<String> stringResponseEntity = restTemplate.getForEntity(url, String.class);
            s = stringResponseEntity.toString();
        } catch (Exception e) {
            log.error("模板消息-获取行业");
            e.printStackTrace();
        }finally {
            return ResultUtils.getSuccessResult(s);
        }
    }



    /**
     * 获取行业模板id
     * @return
     */
    @PostMapping("/getTemplate")
    public Map<String,Object> getTemplate(@RequestBody String body) throws WxErrorException {
        String s = "";
        String url="https://api.weixin.qq.com/cgi-bin/template/api_add_template?access_token="+wxMpService.getAccessToken();
        try {
            ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(url,body,String.class);
            s = stringResponseEntity.toString();
        } catch (Exception e) {
            log.error("模板消息-获取行业");
            e.printStackTrace();
        }finally {
            return ResultUtils.getSuccessResult(s);
        }
    }



    /**
     * 获取行业模板list
     *
     * @return
     */
    @PostMapping("/getTemplateList")
    public Map<String,Object> getTemplateList(@RequestBody String body) throws WxErrorException {
        String s = "";
        String url="https://api.weixin.qq.com/cgi-bin/template/get_all_private_template?access_token="+wxMpService.getAccessToken();
        try {
            ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(url,body, String.class);
            s = stringResponseEntity.toString();
        } catch (Exception e) {
            log.error("模板消息-获取行业");
            e.printStackTrace();
        }finally {
            return ResultUtils.getSuccessResult(s);
        }
    }


    /**
     *  系统登录时、给关注公众号的用户推消息(当前写死退给管理员)
     * @param userCode
     * @return
     */
    @RequestMapping("/userLoginInfo")
    public Map<String,Object> userLoginInfo(String userCode){
        log.info("入参：{}",userCode);
        //1.根据userCode查询对应的openid
        QueryWrapper<User> eq = new QueryWrapper<User>().eq("isDelete", 0).eq("userName", userCode);
        User one = userService.getOne(eq);
        if(one==null||StringUtils.isEmpty(one.getOpenId())){
            log.error("用户不存在或没有订阅公众号");
            return ResultUtils.getFailResult("用户不存在或没有订阅公众号");
        }
        //2.根据openid查询是否订阅公众号
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String url="http://learn.hhf.com";
        //实例化模板对象
        WxMpTemplateMessage wxMpTemplateMessage = new WxMpTemplateMessage();
        //设置模板ID
        wxMpTemplateMessage.setTemplateId(loginTemplate);
        //设置发送给哪个用户
        // 根据userCode查询某个用户
        wxMpTemplateMessage.setToUser(one.getOpenId());
        wxMpTemplateMessage.setUrl(url);
        //构建消息格式
        List<WxMpTemplateData> listData = Arrays.asList(
                new WxMpTemplateData("userName", one.getName(), "blue"),
                new WxMpTemplateData("time", dateTimeFormatter.format(LocalDateTime.now()), "red"),
                new WxMpTemplateData("url", url,"#173177")
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


    /**
     * 素材文件
     * @param wxMaterialDto
     * @return
     */
    public Map<String,Object> wxMaterialPage(WxMaterialDto wxMaterialDto) {
        WxMpMaterialFileBatchGetResult wxMpMaterialFileBatchGetResult = null;
        try {
            wxMpMaterialFileBatchGetResult = wxMpService.getMaterialService().materialFileBatchGet(wxMaterialDto.getType(), (wxMaterialDto.getCurrent() - 1) * wxMaterialDto.getSize(), wxMaterialDto.getSize());
        } catch (WxErrorException e) {
            log.info("获取微信素材列表失败", e);

        }
        return ResultUtils.getSuccessResult(wxMpMaterialFileBatchGetResult);
    }


    /**
     * 图文
     * @param wxMaterialDto
     * @return
     */
    public Map<String,Object>  wxMaterialNewsPage(WxMaterialDto wxMaterialDto) {
        WxMpMaterialNewsBatchGetResult wxMpMaterialNewsBatchGetResult = null;
        try {
            wxMpMaterialNewsBatchGetResult = wxMpService.getMaterialService().materialNewsBatchGet((wxMaterialDto.getCurrent() - 1) * wxMaterialDto.getSize(), wxMaterialDto.getSize());
        } catch (WxErrorException e) {
            log.info("获取微信素材图文列表失败", e);
        }
        return ResultUtils.getSuccessResult(wxMpMaterialNewsBatchGetResult);
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
