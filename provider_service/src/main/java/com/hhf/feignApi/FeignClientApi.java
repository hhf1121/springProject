package com.hhf.feignApi;

import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "ismy",url = "http://localhost:9080/")
public interface FeignClientApi {

    @RequestLine("GET ffpt")
    String ffpt();

}