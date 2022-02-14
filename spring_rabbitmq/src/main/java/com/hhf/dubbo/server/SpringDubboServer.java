package com.hhf.dubbo.server;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class SpringDubboServer {

    public static void main(String[] args) throws IOException {
//        ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("spring-porvider.xml");
        ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("spring-zk-porvider.xml");
        context.start();
        System.in.read();
    }
}
