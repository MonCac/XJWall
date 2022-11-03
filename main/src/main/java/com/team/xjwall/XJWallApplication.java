package com.team.xjwall;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.team"})
@MapperScan("com.team.xjwall.mapper")
public class XJWallApplication {
    public static void main(String[] args) {
        SpringApplication.run(XJWallApplication.class,args);
    }
}


