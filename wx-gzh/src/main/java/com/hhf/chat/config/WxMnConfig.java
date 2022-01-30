package com.hhf.chat.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 *
 * @author hehongfei
 * @Description:    小程序配置类
 * @date 2021-08-25
 */

@Data
@Component
@ConfigurationProperties(prefix = "mini")
public class WxMnConfig {

    private String AppId;

    private String AppSecret;

    private String token;
}
