package com.cest.annotation;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect(types = TimeCheckToAspect.class)
public class Order implements IOrder {

    private int money = 0;

    @Override
    public void pay() throws InterruptedException {
        Thread.sleep(1000);

    }

    @Override
    public void show() {
        log.info("now money is " + money);
    }
}
