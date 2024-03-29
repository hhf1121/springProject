package com.hhf.chat.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 *
 * @author hehongfei
 * @Description: 公众号配置类
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
