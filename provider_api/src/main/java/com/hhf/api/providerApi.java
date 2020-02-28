package com.hhf.api;

import com.hhf.util.SentienlUtil;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * springCloud接口：feign
 */
@FeignClient(value = "provider-service",fallbackFactory = SentienlUtil.class)
public interface providerApi {

    @RequestMapping("/getDataByFeign/{yes}")
    public Map<String,Object> getDataByFeign(@PathVariable("yes") Integer yes);

    @RequestMapping("/book/updateCount")
    public Map<String,Object> updateCount(@RequestParam("id") Long id);

}


