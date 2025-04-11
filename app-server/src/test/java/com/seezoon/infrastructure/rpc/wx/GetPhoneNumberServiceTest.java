package com.seezoon.infrastructure.rpc.wx;

import com.seezoon.BaseApplicationTests;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class GetPhoneNumberServiceTest extends BaseApplicationTests {

    @Autowired
    private GetPhoneNumberService getPhoneNumberService;
    @Autowired
    private StableAccessTokenService stableAccessTokenService;

    @Test
    void execute() {
        String accessToken = stableAccessTokenService.execute("wx76561e919bb4753a",
                "9946a3c0127a810648307815edf1f8fb");
        String phoneNumber = getPhoneNumberService.execute(accessToken,
                "5a8f4c70c69c352d7b50258d74d51c029e94c7e221ce4b909f1f0b754dbf3265");
        System.out.println(phoneNumber);
    }
}