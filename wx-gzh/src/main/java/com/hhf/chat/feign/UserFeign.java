package com.hhf.chat.feign;


import com.alibaba.fastjson.JSONObject;
import com.hhf.chat.entity.User;
import com.hhf.chat.feign.fallback.UserFeignFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@FeignClient(
        name = "userApi",
        url = "http://learn.hhf.com:8082/api",
        fallback = UserFeignFallBack.class,
        configuration = UserFeignConfiguration.class
)
public interface UserFeign {


    @RequestMapping(value = "/registUser",method = RequestMethod.POST)
    Map<String,Object> registUser(@RequestBody User user);

    @RequestMapping(value = "/loginUser",method = RequestMethod.POST)
    public Map<String,Object> loginUser(@RequestBody User user);

}
