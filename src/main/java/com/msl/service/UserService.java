package com.msl.service;

import com.msl.pojo.User;



public interface UserService {

    User findByUsername(String username);

    void register(String username, String password);
}
