package com.hhf.dubbo.annotation.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.hhf.dubbo.annotation.service.ApiService;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service(version = "0.0.1",interfaceClass = ApiService.class,interfaceName = "com.hhf.dubbo.annotation.service.ApiService")
@org.springframework.stereotype.Service
public class ApiServiceImp implements ApiService {


    @Override
    public String getDataByInfo(String info) {
        log.info("调用成功...");
        return "is me!=====>"+info;
    }
}
