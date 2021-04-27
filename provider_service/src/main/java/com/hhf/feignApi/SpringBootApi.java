package com.hhf.feignApi;

import com.hhf.feignApi.fallback.SpringBootApiFallBack;
import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.Map;

/**
 * 请求微服务api，用微服务名称调用
 */

@FeignClient(value = "hhf-springboot",url = "http://localhost:8081/",fallback = SpringBootApiFallBack.class)
public interface SpringBootApi {

    @RequestLine("GET /springBoot/vue/deleteByVue?id={id}")
    public Map<String, Object> deleteUserById(@Param("id") Long id);

}
