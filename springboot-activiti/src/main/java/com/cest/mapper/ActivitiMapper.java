package com.cest.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface ActivitiMapper {

    //删除表单
    @Delete("DELETE FROM formdata WHERE PROC_DEF_ID_ = #{PROC_DEF_ID}")
    int DeleteFormData(@Param("PROC_DEF_ID") String PROC_DEF_ID);
}
