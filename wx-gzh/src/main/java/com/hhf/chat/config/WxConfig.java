package com.hhf.chat.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 云路供应链科技有限公司 版权所有 © Copyright 2020
 *
 * @author hehongfei
 * @Description:
 * @date 2021-08-25
 */

@Data
@Component
@ConfigurationProperties(prefix = "wx")
public class WxConfig {

    private String AppId;

    private String AppSecret;

    private String token;
}
