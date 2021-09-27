package com.cest.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import java.util.UUID;

@Slf4j
public class UseMDC {

    // 使用 MDC 之前, 需要先去配置 %X{REQUEST_ID}
    private static final String FLAG = "REQUEST_ID";

    // ex1
    private static void mdc01() {
        MDC.put(FLAG, UUID.randomUUID().toString());
        log.info("log in mdc01");

        mdc02 ();

        log.info("mdc flag is : [{}]",MDC.get(FLAG));
        MDC.remove(FLAG);
        log.info("mdc flag is : [{}]",MDC.get(FLAG));

    }

    private static void mdc02 () {

        log.info("log in mdc02");
    }

    // ex2
    private class MyThread extends Thread {
        private final String name;

        private MyThread(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            MDC.put(FLAG,UUID.randomUUID().toString());
            log.info("start with multithreading:[{}]",this.name);

            try {
                Thread.sleep(1200);
            } catch (InterruptedException e) {
                log.info(e.getMessage());
            }

            log.info("end with multithreading:[{}]",this.name);
            MDC.remove(FLAG);
        }
    }

    private void multiThreadUseMdc() {
        new MyThread("mdc1").start();
        new MyThread("mdc2").start();
    }

    public static void main(String[] args) {
//        mdc01();

        new UseMDC().multiThreadUseMdc();
    }
}
