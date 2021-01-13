package com.hhf.dubbo.server;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.ServiceConfig;
import com.hhf.dubbo.service.IMyService;
import com.hhf.dubbo.service.MyService;

import java.io.IOException;


public class MyServer {

    public static void main(String[] args) throws IOException {
        // 服务配置
        ServiceConfig<IMyService> serviceConfig = new ServiceConfig<IMyService>();
        // 设置服务接口
        serviceConfig.setInterface(IMyService.class);
        // 设置开放的协议
        serviceConfig.setProtocol(new ProtocolConfig("dubbo",20880));
        // 设置一个空的注册中心
        serviceConfig.setRegistry(new RegistryConfig(RegistryConfig.NO_AVAILABLE));
        // 设置服务当前所在的应用
        serviceConfig.setApplication(new ApplicationConfig("simpl-server"));
        // 设置服务实现对象
        serviceConfig.setRef(new MyService());
        // 暴露服务
        serviceConfig.export();

        System.out.println("服务已经开启："+20880);
        System.in.read();
    }
}
