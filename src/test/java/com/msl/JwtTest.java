package com.msl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTest {


    @Test
    public void testGen() {

        Map<String, Object> claims = new HashMap<>();
        claims.put("id", 1);
        claims.put("username", "msl");

        // 创建令牌
        String token = JWT.create()
                .withClaim("user", claims)   // 设置载荷
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 12))       //设置有效期
                .sign(Algorithm.HMAC256("msl"));     //指定算法,配置秘钥

        System.out.println(token);
    }


    @Test
    public void testVerify() {
        // 定义字符串
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9" +
                ".eyJ1c2VyIjp7ImlkIjoxLCJ1c2VybmFtZSI6Im1zbCJ9LCJleHAiOjE3MjE1MjkyMjJ9" +
                ".wUZaP5c331h4zRH9cyuttoPleR-BMZ_2pEtsxCodV94";


        // 验证令牌

        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256("msl")).build();

        DecodedJWT decodedJWT = jwtVerifier.verify(token);

        Map<String, Claim> claims = decodedJWT.getClaims();

        System.out.println(claims.get("user"));
    }

}
