package com.hhf.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * springCloud接口：feign
 */
@FeignClient("provider-service")
public interface providerApi {

    @RequestMapping("/getDataByFeign/{yes}")
    public Map<String,Object> getDataByFeign(@PathVariable("yes") Integer yes);

}


