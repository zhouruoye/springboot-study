package com.cest.config;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Test {

    public static void main(String[] args) {
        String str = "111111111|111111111|910000000|010000000|011000000|010000001|802000000|802003000|010112317";
        List<String> list = Stream.of(str.split("\\|")).collect(Collectors.toList());

        String lastOne = getLastOne(list);

        System.out.println(lastOne);

    }


    static String getLastOne(List<String> list) {
        String s1 = list.get(list.size()-1);
        String s2 = list.get(list.size()-2);
        if("111111111".equals(s2) && !"111111111".equals(s1)) {
            return s2;
        }
        list.remove(list.size() - 1);
        System.out.println(s1);
        return getLastOne(list);
    }
}
