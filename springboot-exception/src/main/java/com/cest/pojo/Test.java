package com.cest.pojo;

import java.util.Calendar;
import java.util.Date;

public class Test {


    public static void main(String[] args) {
        Calendar calendar = Calendar.getInstance();
        // 时
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        // 分
        calendar.set(Calendar.MINUTE, 0);
        // 秒
        calendar.set(Calendar.SECOND, 0);
        // 毫秒
        calendar.set(Calendar.MILLISECOND, 0);

        Date time = calendar.getTime();
        System.out.println(time);

        User user = new User();
        user.setId(1121L);
        Object t = user;
        if(t != null) {
            String s = t.toString();
            System.out.println(t.toString());
        }

    }
}
