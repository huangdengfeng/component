package com.seezoon.infrastructure.rpc.wx;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.seezoon.BaseApplicationTests;
import com.seezoon.infrastructure.rpc.wx.dto.Code2SessionResp;
import com.seezoon.infrastructure.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
class Code2SessionServiceTest extends BaseApplicationTests {

    @Autowired
    private Code2SessionService code2SessionService;

    @Test
    void execute() throws JsonProcessingException {
        Code2SessionResp resp = code2SessionService.execute("wx76561e919bb4753a", "9946a3c0127a810648307815edf1f8fb",
                "0c3LTC0w3tNX933ubV2w3AXdhp3LTC0P");
        log.info(JsonUtils.toJson(resp));
    }
}