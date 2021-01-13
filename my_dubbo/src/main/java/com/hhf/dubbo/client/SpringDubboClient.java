package com.hhf.dubbo.client;

import com.hhf.dubbo.pojo.User;
import com.hhf.dubbo.service.IMyService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.Scanner;

public class SpringDubboClient {

    public static void main(String[] args) throws IOException {
//        ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("spring-consumer.xml");
        ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("spring-zk-consumer.xml");
        IMyService myService = context.getBean("myService", IMyService.class);
        Scanner scanner = new Scanner(System.in);
        String read="";
        while (!"exit".equals(read)){
            read = scanner.next();
            try{
                User data = myService.getData(read);
                System.out.println(data);
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }

    }
}
