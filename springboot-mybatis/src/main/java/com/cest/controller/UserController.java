package com.cest.controller;

import com.alibaba.fastjson.JSON;
import com.cest.pojo.User;
import com.cest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    @Qualifier("userServiceImpl")
    private UserService userService;

    @Autowired
    @Qualifier("userMysql2ServiceImpl")
    private UserService userService1;

    @RequestMapping("/list")
    public String getAllUser(){
        List<User> allUsers = userService.getAllUsers();
        return JSON.toJSONString(allUsers);
    }

    @RequestMapping("/list1")
    public String getAllUser2(){
        List<User> allUsers = userService1.getAllUsers();
        return JSON.toJSONString(allUsers);
    }
}
