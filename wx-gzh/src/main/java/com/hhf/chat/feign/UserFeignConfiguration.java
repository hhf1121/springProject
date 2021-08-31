package com.hhf.chat.feign;

import feign.Contract;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 云路供应链科技有限公司 版权所有 © Copyright 2020
 *
 * @author hehongfei
 * @Description:
 * @date 2021-08-31
 */

@Configuration
public class UserFeignConfiguration {


    @Bean
    public Contract feignContract() {
        return new SpringMvcContract();
    }

}
