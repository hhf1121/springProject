package com.hhf.chat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.hhf.chat.feign")
public class GzhApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(GzhApplication.class, args);
    }

    protected SpringApplicationBuilder config(SpringApplicationBuilder applicationBuilder){
        return applicationBuilder.sources(GzhApplication.class);
    }

}
