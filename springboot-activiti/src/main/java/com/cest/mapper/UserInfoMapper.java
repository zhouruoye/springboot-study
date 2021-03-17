package com.cest.mapper;

import com.cest.pojo.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface UserInfoMapper {

    /**
     * 从数据库中查询用户
     * @param username
     * @return
     */
    @Select("select * from user where username = #{username}")
    UserInfo selectByUsername(@Param("username") String username);
}
