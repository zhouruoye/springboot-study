package com.cest.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.stream.IntStream;

/**
 * Created by cestlavie on 2019/12/13.
 */
@Service
public class UserService {

    @Async
    public void sendSms(){
        System.out.println("子线程 ----- 1");
        sendMsg1();
        IntStream.range(0, 5).forEach(d -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        sendMsg2();
        System.out.println("子线程 ----- 2");
        sendMsg3();
    }

    public void sendMsg1(){
        System.out.println("子线程1 ---- 1");
        throw new RuntimeException("1111");
    }

    public void sendMsg2(){
        System.out.println("子线程2 ---- 2");
    }

    public void sendMsg3(){
        System.out.println("子线程3 ---- 3");
    }
}
