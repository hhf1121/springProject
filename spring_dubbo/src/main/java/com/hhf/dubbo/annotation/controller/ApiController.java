package com.hhf.dubbo.annotation.controller;


import com.hhf.dubbo.annotation.apiservice.MyTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/api")
@RestController
public class ApiController {

    @Autowired
    public MyTestService myTestService;

    @GetMapping("/getData")
    public String getDataByRPC(String info){
        return myTestService.getDataByRPC(info);
    }

}
