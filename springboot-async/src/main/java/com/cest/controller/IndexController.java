package com.cest.controller;

import com.cest.service.AsyncFutureService;
import com.cest.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by cestlavie on 2019/12/13.
 */
@Slf4j
@RestController
public class IndexController {

    @Autowired
    private UserService userService;

    @Autowired
    private AsyncFutureService asyncFutureService;

    /**
     * 无须知道执行结果
     * @return
     */
    @RequestMapping("/noResultAsync")
    public String NoResultAsync(){
        System.out.println("主流程 -------------- 1");
        userService.sendSms();
        System.out.println("主流程 -------------- 2");
        return "success";
    }

    /**
     * 知道执行结果
     * @return
     */
    @RequestMapping("/getResultAsync")
    public String GetResultAsync() throws InterruptedException, ExecutionException, TimeoutException {
        System.out.println("主流程 -------------- 1");

        Future<String> stringFuture1 = asyncFutureService.task1();
        Future<String> stringFuture2 = asyncFutureService.task2();
        Future<String> stringFuture3 = asyncFutureService.task3();
        log.info("");
        long firstTime = System.currentTimeMillis();

        //如果都执行往就可以跳出循环,isDone方法如果此任务完成，true
        long tmpTime = firstTime;
        for(;;){
            String s = stringFuture1.get(3000, TimeUnit.MILLISECONDS);
            System.out.println("s:" + s);
            //long secondTime = System.currentTimeMillis();
            //long mergeTime = secondTime - tmpTime;
            //if(mergeTime > 500){
            //    log.info("mergeTime");
            //    tmpTime = secondTime;
            //}
            if (stringFuture1.isDone() && stringFuture2.isDone() && stringFuture3.isDone()) {
                break;
            }
            String s1 = stringFuture1.get();
            String s2 = stringFuture2.get();
            String s3 = stringFuture3.get();
            System.out.println(s3);
        }
        long endTime = System.currentTimeMillis();
        log.info("总耗时:{}",(endTime - firstTime));
        System.out.println("主流程 -------------- 2");
        return "success";
    }

}
