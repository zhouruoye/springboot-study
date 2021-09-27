package com.cest.pojo;

import lombok.*;

/**
 * Created by cestlavie on 2019/12/23.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class User {

    private String id;

    private String name;

    private String add;

    private String old;
}
