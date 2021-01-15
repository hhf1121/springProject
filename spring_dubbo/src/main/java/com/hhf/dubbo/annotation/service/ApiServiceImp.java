package com.hhf.dubbo.annotation.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.hhf.dubbo.annotation.service.ApiService;


@Service(version = "0.0.1",interfaceClass = ApiService.class,interfaceName = "com.hhf.dubbo.annotation.service.ApiService")
@org.springframework.stereotype.Service
public class ApiServiceImp implements ApiService {


    @Override
    public String getDataByInfo(String info) {
        return "is me!=====>"+info;
    }
}
