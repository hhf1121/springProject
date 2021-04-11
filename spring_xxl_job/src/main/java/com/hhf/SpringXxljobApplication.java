package com.hhf;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@ComponentScan(basePackages = "com.hhf.oracle")
@MapperScan("com.hhf.oracle.mapper")//mp自定义mapper.xml的时候，绑定失败，需要加上此注解。
public class SpringXxljobApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringXxljobApplication.class, args);
    }

}
