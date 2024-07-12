package com.msl.controller;

import com.msl.pojo.Result;
import com.msl.pojo.User;
import com.msl.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    @PostMapping("/register")
    public Result register(String username, String password) {
        //查询用户
        User u = userService.findByUsername(username);
        if (u == null){
            //没有占用
            //注册
            userService.register(username, password);
            return Result.success();
        }else{
            return Result.error("用户名已被占用");
        }
    }

}
