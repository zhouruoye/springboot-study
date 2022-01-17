package com.cest.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Api("用户模块Web接口")
public class UserController {

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }
}
