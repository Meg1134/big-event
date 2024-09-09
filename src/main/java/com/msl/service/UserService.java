package com.msl.service;

import com.msl.pojo.User;

public interface UserService {

    //查找用户
    User findByUsername(String username);

    // 用户注册
    void register(String username, String password);

    //更新用户信息
    void update(User user);

    // 更新头像
    void updateAvatar(String avatarUrl);

    // 更新密码
    void updatePassword(String md5String);
}
