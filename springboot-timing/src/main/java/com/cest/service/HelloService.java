package com.cest.service;

import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by cestlavie on 2019/12/13.
 */
public class HelloService {
    public void sayHello() {
        System.out.println("hello service >>>"+new Date());
    }
}
