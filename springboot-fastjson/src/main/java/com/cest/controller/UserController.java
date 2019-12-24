package com.cest.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.cest.pojo.User;
import com.cest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cestlavie on 2019/12/23.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/list")
    public List<User> list(){
        List<User> list = userService.queryList();
        return list;
    }

    @RequestMapping("/listWithJson")
    public String listWithJson(){
        List<User> list = userService.queryList();
        return JSON.toJSONString(list);
    }

    @RequestMapping("/listWithNull")
    public String listWithNull(){
        List<User> list = new ArrayList<>();
        return JSON.toJSONString(list, SerializerFeature.WriteNullListAsEmpty);
    }


}
