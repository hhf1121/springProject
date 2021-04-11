package com.hhf.oracle.controller;


import com.hhf.oracle.entity.MyUser;
import com.hhf.oracle.service.IMyUserService;
import com.hhf.util.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/myUser")
public class MyUserController {

    @Autowired
    private IMyUserService myUserService;

    @RequestMapping("/test")
    public String test(){
        return "success";
    }

    @RequestMapping(value = "query",method = RequestMethod.GET)
    public Map<String,Object> query(MyUser myUser){
        return ResultUtils.getSuccessResult(myUserService.queryData(myUser));
    }

    @RequestMapping(value = "save",method = RequestMethod.POST)
    public Map<String,Object> save(@RequestBody MyUser myUser){
        return ResultUtils.getSuccessResult(myUserService.saveData(myUser));
    }

    @RequestMapping(value = "callProcedure",method = RequestMethod.GET)
    public Map<String,Object> callMyProcedureInsertData(Integer num){
        return ResultUtils.getSuccessResult(myUserService.callMyProcedure(num));
    }




}
