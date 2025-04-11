package com.seezoon.infrastructure.rpc.aliyun;

import com.seezoon.BaseApplicationTests;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class CaptchaVerifyerTest extends BaseApplicationTests {

    @Autowired
    private CaptchaVerifyer captchaVerifyer;

    @Test
    void verify() {
        captchaVerifyer.verify("lrazit40", "CaptchaVerifyParam");
    }
}