package com.cest.dao.mysql2;

import com.cest.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMysql2Dao {

    List<User> getAllUsers();
}
