package com.cest.service;

import com.cest.dao.UserDao;
import com.cest.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    //简单查询

    /**
     * 查询所有
     * @return
     */
    public List<User> getUsers(){
        return userDao.findAll();
    }

    public Optional<User> getUser(String id) {
        return this.userDao.findById(id);
    }

    /**
     * 新增和修改都是 save方法，
     * id 存在为修改，id 不存在为新增
     */
    public User createUser(User user) {
        user.setId(null);
        return userDao.save(user);
    }

    public void deleteUser(String id) {
        this.userDao.findById(id)
                .ifPresent(user -> this.userDao.delete(user));
    }

    public void updateUser(String id, User user) {
        this.userDao.findById(id)
                .ifPresent(
                        u -> {
                            u.setName(user.getName());
                            u.setAge(user.getAge());
                            u.setDescription(user.getDescription());
                            this.userDao.save(u);
                        }
                );
    }

    //复杂查询
    public List<User> findByAgeBetween(Integer from, Integer to){
        return userDao.findByAgeBetween(from,to);
    }
}
