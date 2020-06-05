package com.cest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = "com.cest.mapper")
@ComponentScan(basePackages = {"com.cest"})
public class SpringbootPoiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootPoiApplication.class, args);
    }

}
