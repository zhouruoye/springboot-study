package com.cest.annotation;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TimeCheckToAspect implements IAspect{

    long start = 0;

    @Override
    public void before() {
        start = System.currentTimeMillis();
        log.info("start time is" + start);
    }

    @Override
    public void after() {
        long costTime = System.currentTimeMillis() - start;
        log.info("cost time is:" + costTime);
    }
}
