package com.msl.interceptors;

import com.msl.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    // 拦截器的前置方法，作用是在请求到达Controller之前执行
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //进行令牌验证
        String token = request.getHeader("Authorization");
        //验证token
        try{
            Map<String, Object> claims = JwtUtil.parseToken(token);
            // 如果是True，没有异常，就放行
            return true;
        }catch (Exception e){
            // 校验失败，响应码返回401
            response.setStatus(401);
            return false;
        }
    }
}
