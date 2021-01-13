package com.hhf.dubbo.service;

public class MyService implements IMyService{
    @Override
    public String getData(String info) {
        return "hello,"+info;
    }
}
