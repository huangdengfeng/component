package com.seezoon.infrastructure.utils;

import com.seezoon.domain.service.sys.authentication.support.JwtToken;
import com.seezoon.domain.service.sys.authentication.valueobj.TokenInfoVO;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Map;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * jwt 工具类测试
 *
 * @author huangdengfeng
 * @date 2023/9/8 12:40
 */
class JwtTokenTest {

    @Test
    void testNormal() {
        String signKey = RandomStringUtils.randomAlphanumeric(32);
        JwtToken jwtToken = new JwtToken(signKey);
        String subject = "uid";
        String tokenId = "tokenId";
        String checkSum = "checkSum";
        Duration expiration = Duration.ofSeconds(10);
        String token = jwtToken.create(new TokenInfoVO(subject, tokenId, Map.of("checkSum", checkSum)), expiration);
        TokenInfoVO tokenInfo = jwtToken.getTokenInfo(token);
        Assertions.assertNotNull(tokenInfo);
        Assertions.assertEquals(subject, tokenInfo.getSubject());
        Assertions.assertEquals(tokenId, tokenInfo.getTokenId());
    }

    @Test
    void testCreateExpire() throws InterruptedException {
        String signKey = RandomStringUtils.randomAlphanumeric(32);
        JwtToken jwtToken = new JwtToken(signKey);
        String subject = "uid";
        String tokenId = "tokenId";
        String checkSum = "checkSum";
        Duration expiration = Duration.ofSeconds(5);
        String token = jwtToken.create(new TokenInfoVO(subject, tokenId, Map.of("checkSum", checkSum)), expiration);
        TokenInfoVO tokenInfo = jwtToken.getTokenInfo(token);
        String subj1 = tokenInfo.getSubject();
        Assertions.assertEquals(subject, subj1);

        Thread.sleep(Duration.ofSeconds(6).toMillis());
        tokenInfo = jwtToken.getTokenInfo(token);
        Assertions.assertNull(tokenInfo);
    }

    @Test
    void testStaticDataPass() {
        String signKey = "12345678901234567890123456789012";
        JwtToken jwtToken = new JwtToken(signKey);
        String subject = "uid";
        String tokenId = "tokenId";
        String checkSum = "checkSum";
        LocalDate localDate = LocalDate.of(2099, 11, 10);
        String token = jwtToken.create(new TokenInfoVO(subject, tokenId, Map.of("checkSum", checkSum)),
                localDate.atStartOfDay());
        TokenInfoVO tokenInfo = jwtToken.getTokenInfo(token);
        String subj = tokenInfo.getSubject();
        Assertions.assertEquals(subject, subj);
        Assertions.assertEquals(checkSum, tokenInfo.getClaims().get("checkSum"));
    }

    @Test
    void testStaticDataExpire() {
        String signKey = "12345678901234567890123456789012";
        JwtToken jwtToken = new JwtToken(signKey);
        String subject = "uid";
        String tokenId = "tokenId";
        String checkSum = "checkSum";
        LocalDate localDate = LocalDate.of(2023, 9, 8);
        String token = jwtToken.create(new TokenInfoVO(subject, tokenId, Map.of("checkSum", checkSum)),
                localDate.atStartOfDay());
        TokenInfoVO tokenInfo = jwtToken.getTokenInfo(token);
        Assertions.assertNull(tokenInfo);
    }
}