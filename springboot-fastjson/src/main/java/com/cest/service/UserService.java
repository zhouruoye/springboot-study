package com.cest.service;

import com.cest.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by cestlavie on 2019/12/23.
 */
@Slf4j
@Service
public class UserService {

    public List<User> queryList(){
        List<User> list = new ArrayList<>();
        list.add(User.builder().id("1").old("12").name("张三").build());
        list.add(User.builder().id("2").old("12").name("李四").add("汉口").build());
        list.add(User.builder().id("3").old("12").name("周栈").add("青山").build());
        list.add(User.builder().id("4").old("12").name("周栈").add("青山").build());

        return list;
    }


    public List<User> queryList2(List<String> crdIds,String type){
        log.info("-----------------进入查询员工方法---------------");
        List<User> list = new ArrayList<>();
        list.add(User.builder().id("1").old("12").name("张三").build());
        list.add(User.builder().id("2").old("12").name("李四").add("汉口").build());
        list.add(User.builder().id("3").old("12").name("周栈").add("青山").build());
        list.add(User.builder().id("4").old("12").name("周栈").add("青山").build());
        log.info("-----------------结束查询员工方法---------------");
        if(!CollectionUtils.isEmpty(crdIds)) {
            return list.stream().filter(n -> crdIds.contains(n.getId())).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

}
