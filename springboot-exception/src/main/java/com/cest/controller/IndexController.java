package com.cest.controller;

import com.cest.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cestlavie on 2019/12/18.
 */
@Controller
public class IndexController {

    //http://localhost:8080/index
    @GetMapping("/index")
    public String index(Model model) {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            User u = new User();
            u.setId((long) i);
            u.setName("javaboy:" + i);
            u.setAddress("深圳:" + i);
            users.add(u);
        }
        model.addAttribute("users", users);
        return "index";
    }

    //http://localhost:8080/hello 异常
    @GetMapping("/hello")
    public String hello(Model model) {
        int i = 1/0;
        return "hello";
    }
}
