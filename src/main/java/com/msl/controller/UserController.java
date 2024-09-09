package com.msl.controller;


import com.msl.pojo.Result;
import com.msl.pojo.User;
import com.msl.service.UserService;
import com.msl.utils.JwtUtil;
import com.msl.utils.Md5Util;
import com.msl.utils.ThreadLocalUtil;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
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
    public Result<User> userInfo(/*@RequestHeader("Authorization") String token*/) {
        // 根据用户名查询用户信息
        // Map<String, Object> claims = JwtUtil.parseToken(token);
        // String username = (String) claims.get("username");

        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User user = userService.findByUsername(username);
        return Result.success(user);
    }


    // @RequestBody注解用于接收前端传递给后端的json字符串中的数据,并将其转换为java对象
    @PutMapping("/update")
    public Result update(@RequestBody @Validated User user){
        userService.update(user);
        return Result.success();

    }

    @PatchMapping("/updateAvatar")
    public Result updateAvatar(@RequestParam @URL String avatarUrl){
        userService.updateAvatar(avatarUrl);
        return Result.success();
    }


    @PatchMapping("/updatePwd")
    public Result updatePassword(@RequestBody Map<String, String> params){
        //1. 校验参数
        String oldPwd = params.get("old_pwd");
        String newPwd = params.get("new_pwd");
        String rePwd = params.get("re_pwd");
        if(!StringUtils.hasLength(oldPwd) || !StringUtils.hasLength(newPwd) || !StringUtils.hasLength(rePwd)){
            return Result.error("缺少必要的参数");
        }
        // 校验原密码是否正确
        // 调用userService的方法，根据用户名查询原密码，再和old_pwd进行对比
        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User loginUser = userService.findByUsername(username);
        if (!loginUser.getPassword().equals(Md5Util.getMD5String(oldPwd))) {
            return Result.error("原密码错误");
        }

        //newPwd和rePwd是否一致
        if(!rePwd.equals(newPwd)){
            return Result.error("两次填写的新密码是不一样的");
        }

        //2.调用service层的方法，更新密码
        userService.updatePassword(Md5Util.getMD5String(newPwd));
        return Result.success();
    }
}
