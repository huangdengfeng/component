package com.seezoon.infrastructure.rpc.jwt;

import com.seezoon.BaseApplicationTests;
import com.seezoon.infrastructure.security.JwtService;
import java.time.Duration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


class JwtServiceTest extends BaseApplicationTests {

    @Autowired
    private JwtService jwtService;

    @Test
    void sign() {
        String test = jwtService.sign("test", Duration.ofHours(2));
        System.out.println(test);
    }

    @Test
    void verfiy() {
        String verify = jwtService.verify(
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE3MjA0ODU2NDksInN1YiI6InRlc3QifQ.mAeYZJZnY29xrDtr-ZO6JA7zXDUWGbMEsC7SG5d8ZhY");
        System.out.println(verify);
    }
}