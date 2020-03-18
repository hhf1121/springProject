package com.hhf.feignApi;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * 请求微服务api
 */

@FeignClient(value = "hhf-springboot")
public interface SpringBootApi {

    @RequestMapping("/springBoot/vue/deleteByVue")
    public Map<String, Object> deleteUserById(@RequestParam("id") Long id);

}
