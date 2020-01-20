package com.cest.dao;

import com.cest.pojo.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 泛型分别为实体对象和主键类型。通过继承MongoRepository
 */
@Repository
public interface UserDao extends MongoRepository<User,String> {

    /**
     * 根据年龄段来查找
     * @param from from
     * @param to   to
     * @return List<User>
     */
    List<User> findByAgeBetween(Integer from, Integer to);

}
