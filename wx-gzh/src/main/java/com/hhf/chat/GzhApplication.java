package com.hhf.chat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.hhf.chat.feign")
public class GzhApplication {

    public static void main(String[] args) {
        SpringApplication.run(GzhApplication.class, args);
    }

}
