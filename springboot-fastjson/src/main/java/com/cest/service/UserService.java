package com.cest.service;

import com.cest.pojo.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cestlavie on 2019/12/23.
 */
@Service
public class UserService {

    public List<User> queryList(){
        List<User> list = new ArrayList<>();
        list.add(User.builder().id(1).old("12").name("张三").build());
        list.add(User.builder().id(1).old("12").name("李四").add("汉口").build());
        list.add(User.builder().id(1).old("12").name("王五").add("洪山").build());
        list.add(User.builder().id(1).old("12").name("周栈").add("青山").build());

        return list;
    }
}
