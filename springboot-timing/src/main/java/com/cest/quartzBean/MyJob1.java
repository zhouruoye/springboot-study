package com.cest.quartzBean;

import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 首先将这个 Job 注册到 Spring 容器中。
 * 这种定义方式有一个缺陷，就是无法传参。
 * Created by cestlavie on 2019/12/13.
 */
@Component
public class MyJob1 {
    public void sayHello() {
        System.out.println("MyJob1>>>"+new Date());
    }
}
