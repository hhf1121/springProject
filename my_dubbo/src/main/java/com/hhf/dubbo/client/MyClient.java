package com.hhf.dubbo.client;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.hhf.dubbo.pojo.User;
import com.hhf.dubbo.service.IMyService;

import java.io.IOException;
import java.util.List;

public class MyClient {

    public static void main(String[] args) throws IOException {

        ReferenceConfig<IMyService> referenceConfig = new ReferenceConfig<IMyService>();
        // 设置服务接口
        referenceConfig.setInterface(IMyService.class);
        // 设置URL
//        referenceConfig.setUrl("dubbo://192.168.202.1:20880/com.hhf.dubbo.service.IMyService");
        //使用multicast模式的时候，需要关掉vm虚拟机的虚拟网卡，否则调不到...
        referenceConfig.setRegistry(new RegistryConfig("multicast://224.1.2.3:11111"));
        //轮询
        referenceConfig.setLoadbalance("roundrobin");
        referenceConfig.setUrl(null);
        // 设置服务名称
        referenceConfig.setApplication(new ApplicationConfig("simple-client"));

//        IMyService iMyService = referenceConfig.get();
//        String data = iMyService.getData("你好！");
//        System.out.println(data);

        while (!"exit".equals(System.in.read())){
            try{
                IMyService iMyService = referenceConfig.get();
                User data = iMyService.getData("你好！");
                System.out.println(data);
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    }
}
