package com.cest.controller;

import com.cest.pojo.User;
import com.cest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/list")
    public List<User> list(){
        List<User> list = new ArrayList<>();
        return list;
    }

    @GetMapping
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @RequestMapping("/getUsersByCondition")
    public List<User> getUsersByCondition(Integer from, Integer to) {
        return userService.findByAgeBetween(from,to);
    }

    @PostMapping
    public User createUser(User user) {
        return userService.createUser(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
    }

    @PutMapping("/{id}")
    public void updateUser(@PathVariable String id, User user) {
        userService.updateUser(id, user);
    }

    /**
     * 根据用户 id查找
     * 存在返回，不存在返回 null
     */
    @GetMapping("/{id}")
    public User getUser(@PathVariable String id) {
        return userService.getUser(id).orElse(null);
    }
}
