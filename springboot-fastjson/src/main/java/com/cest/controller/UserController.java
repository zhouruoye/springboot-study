package com.cest.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.cest.pojo.User;
import com.cest.service.UserService;
import com.cest.util.SpringReflectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        try {
            List<String> ids = Stream.of("1","2","3","4").collect(Collectors.toList());
            list = SpringReflectUtils.springInvokeMethod("userService", "queryList2", new Object[]{ids,"1231"}, User.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return JSON.toJSONString(list, SerializerFeature.WriteNullListAsEmpty);
    }


}
