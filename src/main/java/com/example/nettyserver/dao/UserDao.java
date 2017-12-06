package com.example.nettyserver.dao;

import com.example.nettyserver.dao.base.GenericDao;
import com.example.nettyserver.entity.User;

import java.util.List;

public interface UserDao extends GenericDao<User, Long> {
    Boolean has(String userName, String pwd);

    List<User> getUser(String userName, String pwd);
}
