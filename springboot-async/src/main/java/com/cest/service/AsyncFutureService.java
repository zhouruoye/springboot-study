package com.cest.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

/**
 * Created by cestlavie on 2019/12/13.
 */
@Service
@Async
public class AsyncFutureService {

    public Future<String> task1() throws InterruptedException {
        long begin = System.currentTimeMillis();
        Thread.sleep(2000L);
        long end = System.currentTimeMillis();
        System.out.println("任务1耗时="+(end-begin));
        return new AsyncResult<String>("任务1");
    }

    public Future<String> task2() throws InterruptedException {
        long begin = System.currentTimeMillis();
        Thread.sleep(1000L);
        long end = System.currentTimeMillis();
        System.out.println("任务2耗时="+(end-begin));
        return new AsyncResult<String>("任务2");
    }

    public Future<String> task3() throws InterruptedException {
        throw new RuntimeException("出现异常");
        //long begin = System.currentTimeMillis();
        //Thread.sleep(3000L);
        //long end = System.currentTimeMillis();
        //System.out.println("任务3耗时="+(end-begin));
        //return new AsyncResult<String>("任务3");
    }
}
