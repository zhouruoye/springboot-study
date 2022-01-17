package com.cest.dao;

import com.cest.entity.pojo.TbAction;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TbActionDao {
    int deleteByPrimaryKey(Integer id);

    int insert(TbAction record);

    int insertSelective(TbAction record);

    TbAction selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbAction record);

    int updateByPrimaryKey(TbAction record);
}