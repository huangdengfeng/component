package com.seezoon.domain.service.sys.authentication.valueobj;

import com.seezoon.infrastructure.exception.Assertion;
import java.util.Collections;
import java.util.Map;
import lombok.Getter;

/**
 * token 业务字段
 *
 * @author huangdengfeng
 * @date 2023/9/11 23:11
 */
@Getter
public class TokenInfoVO {

    /**
     * 主题字段，业务关键信息
     */
    private String subject;
    /**
     * 用于安全控制
     */
    private String tokenId;
    /**
     * 扩展字段
     */
    private Map<String, Object> claims;

    public TokenInfoVO(String subject, String tokenId) {
        this(subject, tokenId, Collections.emptyMap());
    }

    public TokenInfoVO(String subject, String tokenId, Map<String, Object> claims) {
        Assertion.notEmpty(subject, "subject must not empty");
        Assertion.notEmpty(subject, "tokenId must not empty");
        Assertion.notNull(claims, "claims must not null");
        this.subject = subject;
        this.tokenId = tokenId;
        this.claims = claims;
    }

}
