package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;
@EnableScheduling
@SpringBootApplication
//@ComponentScan(basePackages = "com.example.mybatis")
//@MapperScan("com.example.mybatis")
public class DemoApplication {

    public static void main(String[] args) {
        //UserService userService = new UserService();
        //userService.findById(1);
        SpringApplication.run(DemoApplication.class, args);
        //UserService userService = new UserService();
        //userService.findAll();
        System.out.println("项目运行成功" +
                "");
    }
}
