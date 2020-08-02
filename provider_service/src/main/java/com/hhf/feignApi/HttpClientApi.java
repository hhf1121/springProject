package com.hhf.feignApi;

import com.hhf.entity.User;
import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * 请求第三方url,用http调用
 */

@FeignClient(value = "ishttp",url = "http://localhost:8082/")
public interface HttpClientApi {

    @RequestLine("GET springBoot/getCurrentUserStr?id={id}")
    String getCurrentUserStr(@Param("id") String id);

}