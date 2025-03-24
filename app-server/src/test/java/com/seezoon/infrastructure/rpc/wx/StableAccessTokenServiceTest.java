package com.seezoon.infrastructure.rpc.wx;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.seezoon.BaseApplicationTests;
import com.seezoon.infrastructure.rpc.wx.dto.StableAccessTokenResp;
import com.seezoon.infrastructure.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
class StableAccessTokenServiceTest extends BaseApplicationTests {

    @Autowired
    private StableAccessTokenService stableAccessTokenService;

    @Test
    void execute() throws JsonProcessingException {
        // 使用配置文件中的测试账号
        StableAccessTokenResp resp = stableAccessTokenService.execute("wx76561e919bb4753a",
                "9946a3c0127a810648307815edf1f8fb");
        log.info(JsonUtils.toJson(resp));
    }
} 