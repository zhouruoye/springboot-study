package com.cest.pojo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Test {

    public static void main(String[] args) {
//        UserInfo userInfo = new UserInfo();
//        if(userInfo == null) {
//            userInfo.getAddress();
//            System.out.println(111);
//        }
        Stu stu1 = new Stu("zhangsan",1);
        Stu stu2 = new Stu("zhangsan",1);
        Stu stu3 = new Stu("zhangsan",2);

        List<Stu> stuList = new ArrayList<>();
        stuList.add(stu1);
        stuList.add(stu2);
        stuList.add(stu3);

        List<Stu> collect = stuList.stream().distinct().collect(Collectors.toList());
        System.out.println(111);
    }
}
