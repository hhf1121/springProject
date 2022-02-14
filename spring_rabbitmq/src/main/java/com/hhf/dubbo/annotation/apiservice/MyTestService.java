package com.hhf.dubbo.annotation.apiservice;


import com.alibaba.dubbo.config.annotation.Reference;
import com.hhf.dubbo.annotation.service.ApiService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MyTestService {

    @Reference(version = "0.0.1")
    public ApiService apiService;

    public String getDataByRPC(String info){
        return apiService.getDataByInfo(info);
    }

}
