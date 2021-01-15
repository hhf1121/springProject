package com.hhf.dubbo.server;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.ServiceConfig;
import com.hhf.dubbo.service.IMyService;
import com.hhf.dubbo.service.MyService;

import java.io.IOException;
import java.util.List;


public class MyServer {

    public static void main(String[] args) throws IOException {
        // 服务配置
        ServiceConfig<IMyService> serviceConfig = new ServiceConfig<IMyService>();
        // 设置服务接口
        serviceConfig.setInterface(IMyService.class);
        // 设置开放的协议(-1,不指定端口。20880<=)
        serviceConfig.setProtocol(new ProtocolConfig("com/hhf/dubbo",-1));
        // 设置一个空的注册中心
//        serviceConfig.setRegistry(new RegistryConfig(RegistryConfig.NO_AVAILABLE));
        serviceConfig.setRegistry(new RegistryConfig("multicast://224.1.2.3:11111"));
        // 设置服务当前所在的应用
        serviceConfig.setApplication(new ApplicationConfig("simpl-server"));
        // 设置服务实现对象
        MyService myService = new MyService();
        serviceConfig.setRef(myService);
        // 暴露服务
        serviceConfig.export();
        List<URL> exportedUrls = serviceConfig.getExportedUrls();
        int port = exportedUrls.get(0).getPort();
        myService.setPort(port+"");
        System.out.println("服务已经开启..."+port);
        System.in.read();
    }
}
