package com.cest.service.impl;

import com.cest.dao.mysql2.UserMysql2Dao;
import com.cest.pojo.User;
import com.cest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userMysql2ServiceImpl")
public class UserMysql2ServiceImpl implements UserService {

    @Autowired
    private UserMysql2Dao userDao;

    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }
}
