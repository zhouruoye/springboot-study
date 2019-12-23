package com.cest.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * Created by cestlavie on 2019/12/23.
 */
@Slf4j
@RestController
public class CorsController {

    /**
     * 解决单个跨域 表示可以接受所有请求
     * @return
     */
    //@CrossOrigin("*")
    @GetMapping("/hello")
    public String hello() {
        log.info("get方法");
        return "get hello";
    }

    @PostMapping("/hello")
    public String hello2() {
        log.info("post方法");
        return "post hello";
    }

    /**
     * 解决单个跨域 表示可以接受来自http://localhost:8081的请求
     * @return
     */
    //@CrossOrigin(value = "http://localhost:8081")
    @RequestMapping(value = "/cors")
    public String corsIndex(){
        log.info("cors");
        return "this is cors info.";
    }
}
