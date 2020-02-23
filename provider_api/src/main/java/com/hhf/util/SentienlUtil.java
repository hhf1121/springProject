package com.hhf.util;

import com.hhf.api.providerApi;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class SentienlUtil implements FallbackFactory<providerApi> {

    @Override
    public providerApi create(Throwable throwable) {
        return new providerApi() {
            @Override
            public Map<String, Object> getDataByFeign(Integer yes) {
                log.error("原因:{}",throwable);
                Map<String,Object> map=new HashMap<>();
                map.put("success",false);
                map.put("data","sentinel降级、限流.");
                return map;
            }
        };
    }
}
