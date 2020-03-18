package com.hhf.config;


import feign.Contract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * feign调用需加上该配置，否则报错：
 * Method getLinksForTrack not annotated with HTTP method type (ex. GET, POST)
 * 意思就是feign 默认使用的是spring mvc 注解（就是RequestMapping 之类的)
 * 所以需要通过新增一个配置类来修改其“契约”。
 */
@Configuration
public class FeignConfiguration {
    //使用feign自带契约
    @Bean
    public Contract feignContract() {
        return new Contract.Default();
    }
}