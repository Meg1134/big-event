package com.msl.controller;


import com.msl.pojo.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/article")
public class ArticleController {


    
    @GetMapping("/list")
    // 参数里面传递一个HttpServletResponse对象，方便我们定义响应码
    public Result<String> list(/*@RequestHeader("Authorization") String token, HttpServletResponse response*/) {

        // 验证token
        // try {
        //     Map<String, Object> claims = JwtUtil.parseToken(token);
        //     // 如果是True，没有异常，就放行
        //     return Result.success("文章列表");
        // } catch (Exception e) {
        //     // 校验失败，响应码返回401
        //     response.setStatus(401);
        //     return Result.error("请重新登录");
        // }
        return Result.success("文章列表");
    }
}
