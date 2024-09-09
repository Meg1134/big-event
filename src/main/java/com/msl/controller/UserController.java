package com.msl.controller;


import com.msl.pojo.Result;
import com.msl.pojo.User;
import com.msl.service.UserService;
import com.msl.utils.JwtUtil;
import com.msl.utils.Md5Util;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Result register(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$") String password) {
        User user = userService.findByUsername(username);
        if (user != null) {
            return Result.error("用户名已存在");
        }else{
            userService.register(username, password);
            return Result.success();
        }
    }


    @PostMapping("/login")
    public Result<String> login(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$") String password) {

        User user = userService.findByUsername(username);

        if (user == null) {
            return Result.error("用户名不存在");
        }

        // 判断密码是否正确，因为我们提前对密码进行了加密，所以我们需要对用户输入的密码进行加密后再进行比较

        if (!user.getPassword().equals(Md5Util.getMD5String(password))) {
            return Result.error("密码错误");
        }else{
            // 登录成功，生成token
            Map<String, Object> claims= new HashMap<>();
            claims.put("id",user.getId());
            claims.put("username",user.getUsername());
            String token = JwtUtil.genToken(claims);
            return Result.success(token);

        }

    }

    @GetMapping("/userInfo")
    public Result<User> userInfo(@RequestHeader("Authorization") String token) {
        // 根据用户名查询用户信息
        Map<String, Object> claims = JwtUtil.parseToken(token);
        String username = (String) claims.get("username");
        User user = userService.findByUsername(username);
        return Result.success(user);
    }

}
