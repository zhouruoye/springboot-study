package com.cest.dao;

import com.cest.entity.pojo.TbHolidays;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TbHolidaysDao {
    int deleteByPrimaryKey(Integer id);

    int insert(TbHolidays record);

    int insertSelective(TbHolidays record);

    TbHolidays selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbHolidays record);

    int updateByPrimaryKey(TbHolidays record);
}