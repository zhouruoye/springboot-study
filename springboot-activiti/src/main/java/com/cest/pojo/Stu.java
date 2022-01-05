package com.cest.pojo;

import lombok.Data;

@Data
public class Stu {

    private String name;
    private int age;

    public Stu(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Stu() {
    }
}
