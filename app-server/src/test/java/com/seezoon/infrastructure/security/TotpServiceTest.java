package com.seezoon.infrastructure.security;

import com.seezoon.BaseApplicationTests;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class TotpServiceTest extends BaseApplicationTests {

    @Autowired
    private TotpService totpService;

    @Test
    void validateCode() {
        byte[] secretKey = totpService.generateSecretKey();
        System.out.println(secretKey);
        String code = totpService.generateCode(secretKey);
        System.out.println(code);
        boolean b = totpService.validateCode(secretKey, code);
        System.out.println(b);
    }

}