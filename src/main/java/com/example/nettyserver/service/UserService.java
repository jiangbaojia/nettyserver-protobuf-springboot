package com.example.nettyserver.service;

import com.example.nettyserver.entity.User;

import java.util.List;

public interface UserService {
    Boolean has(String userName, String pwd);

    List<User> getUser(String userName, String pwd);
}
