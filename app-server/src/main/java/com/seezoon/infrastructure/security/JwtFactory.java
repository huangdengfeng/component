package com.seezoon.infrastructure.security;

import com.seezoon.infrastructure.exception.Assertion;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

/**
 * jwt 工具类，只用最简单的功能
 * <pre>
 *     header.payload.signature
 *     claims是payload
 * </pre>
 *
 * @author huangdengfeng
 * @date 2023/9/8 11:37
 */
@Slf4j
public class JwtFactory {

    private final Key signKey;

    public JwtFactory(String signKey) {
        Assertion.isTrue(StringUtils.isNotEmpty(signKey), "sign key must not empty");
        this.signKey = Keys.hmacShaKeyFor(signKey.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * @param subject
     * @param expiration
     * @return
     */
    public String create(String subject, Duration expiration) {
        LocalDateTime localDateTime = LocalDateTime.now().plusSeconds(expiration.toSeconds());
        return this.create(subject, localDateTime);
    }

    /**
     * 颁发token
     *
     * @param subject
     * @param expiration 过期时间
     * @return
     */
    public String create(String subject, LocalDateTime expiration) {
        Assert.hasText(subject, "subject must not empty");
        Assert.notNull(expiration, "expiration must not null");
        Instant expirationInstant = expiration.atZone(ZoneId.systemDefault()).toInstant();
        String result = Jwts.builder()
                .claims()
                .subject(subject)
                .issuedAt(new Date())
                .setExpiration(Date.from(expirationInstant))
                .and().signWith(SignatureAlgorithm.HS256, signKey).compact();
        return result;
    }


    /**
     * 获取主题
     *
     * @param token jwt
     * @return null if token invalid
     */
    public String getSubject(String token) {
        Assert.hasText(token, "token must not empty");
        JwtParser jwtParser = Jwts.parser().setSigningKey(signKey).build();
        // 过期、格式错误、签名错误都是有异常 JwtException
        Jws<Claims> claimsJws = null;
        try {
            claimsJws = jwtParser.parseSignedClaims(token);
        } catch (JwtException e) {
            log.error("jwt parse error:{}", e.getMessage());
            return null;
        }
        Claims body = claimsJws.getBody();
        if (null == body) {
            return null;
        }

        return body.getSubject();
    }
}
