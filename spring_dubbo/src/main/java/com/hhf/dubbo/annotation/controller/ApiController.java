package com.hhf.dubbo.annotation.controller;


import com.hhf.dubbo.annotation.apiservice.MyTestService;
import com.hhf.dubbo.pojo.User;
import com.hhf.stream.rabbitmq.MyProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/api")
@RestController
public class ApiController {

    @Autowired
    public MyTestService myTestService;

    @Value(value = "${server.port}")
    private String port;

    @GetMapping("/getData")
    public String getDataByRPC(String info){
        return myTestService.getDataByRPC(info);
    }

    @Autowired
    private MyProvider myProvider;

    /**
     * 测试spring-cloud-stream-rabbitmq
     * @param name
     * @param age
     * @return
     */
    @GetMapping("/sendMsg")
    public String sendMsg(String name,Integer age){
        User user=new User();
        user.setAge(age);
        user.setName(name);
        user.setAddress("producerIP:"+port);
        myProvider.sendMsg(user);
        return "success";
    }

}
