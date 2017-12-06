package com.example.nettyserver.service;

import com.example.nettyserver.dao.UserDao;
import com.example.nettyserver.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    public Boolean has(String userName, String pwd) {
        return userDao.has(userName, pwd);
    }

    public List<User> getUser(String userName, String pwd){
        return userDao.getUser(userName, pwd);
    }
}
