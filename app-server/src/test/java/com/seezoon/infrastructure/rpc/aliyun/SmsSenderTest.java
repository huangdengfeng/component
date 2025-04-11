package com.seezoon.infrastructure.rpc.aliyun;

import com.seezoon.BaseApplicationTests;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class SmsSenderTest extends BaseApplicationTests {

    @Autowired
    private SmsSender smsSender;

    @Test
    void sendSms() {
        Map<String, String> params = new HashMap<>();
        params.put("code", "1234");
        smsSender.sendSms("13249073372", "阿里云短信测试", "SMS_154950909", params);
    }
}