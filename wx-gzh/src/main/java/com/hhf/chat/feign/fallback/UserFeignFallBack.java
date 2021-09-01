package com.hhf.chat.feign.fallback;

import com.alibaba.fastjson.JSONObject;
import com.hhf.chat.entity.User;
import com.hhf.chat.feign.UserFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 云路供应链科技有限公司 版权所有 © Copyright 2020
 *
 * @author hehongfei
 * @Description:
 * @date 2021-08-31
 */

@Component
@Slf4j
public class UserFeignFallBack implements UserFeign {
    @Override
    public Map<String, Object> registUser(User user) {
        log.info("注册用户调用失败");
        return new JSONObject();
    }

    @Override
    public Map<String, Object> loginUser(User user) {
        log.info("用户登录调用失败");
        return new JSONObject();
    }

    @Override
    public User getUserByOpenid(String openId) {
        log.info("查询用户失败");
        return new User();
    }
}
