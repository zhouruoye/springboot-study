package com.cest.mapper;

import com.cest.my.mapper.MyMapper;
import com.cest.pojo.UserEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UsersMapper extends MyMapper<UserEntity> {

    List<UserEntity> selectAllList();
}