package com.hhf.feignApi;

import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * 请求微服务api，用微服务名称调用
 */

@FeignClient(value = "hhf-springboot")
public interface SpringBootApi {

    @RequestLine("GET /springBoot/vue/deleteByVue?id={id}")
    public Map<String, Object> deleteUserById(@Param("id") Long id);

}
