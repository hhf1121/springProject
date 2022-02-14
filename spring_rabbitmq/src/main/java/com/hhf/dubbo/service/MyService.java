package com.hhf.dubbo.service;


import com.hhf.dubbo.pojo.User;

public class MyService implements IMyService{

    public User isUser;

    public User getIsUser() {
        return isUser;
    }

    public void setIsUser(User isUser) {
        this.isUser = isUser;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    private String port;


    @Override
    public User getData(String info) {
        System.out.println(info+"rpc调用>>>>>"+isUser);
        if(isUser!=null){
            isUser.setName(info);
        }else {
            isUser=new User();
        }
        return isUser;
    }
}
