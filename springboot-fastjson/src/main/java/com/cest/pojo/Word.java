package com.cest.pojo;

import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by cestlavie on 2019/12/24.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class Word {

    private String d;
    private String e;
    private String f;
    private String a;
    private int b;
    private boolean c;
    private Date date;
    private Map<String , Object> map;
    private List<User> list;
}
