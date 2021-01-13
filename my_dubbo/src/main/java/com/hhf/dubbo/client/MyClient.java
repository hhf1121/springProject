package com.hhf.dubbo.client;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.hhf.dubbo.service.IMyService;

import java.io.IOException;

public class MyClient {

    public static void main(String[] args) throws IOException {

        ReferenceConfig<IMyService> referenceConfig = new ReferenceConfig<IMyService>();
        // 设置服务接口
        referenceConfig.setInterface(IMyService.class);
        // 设置URL
        referenceConfig.setUrl("dubbo://127.0.0.1:20880/com.hhf.dubbo.service.IMyService");
        // 设置服务名称
        referenceConfig.setApplication(new ApplicationConfig("simple-client"));

        IMyService iMyService = referenceConfig.get();
        String data = iMyService.getData("你好！");
        System.out.println(data);

    }
}
