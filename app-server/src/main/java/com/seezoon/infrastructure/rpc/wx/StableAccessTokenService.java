package com.seezoon.infrastructure.rpc.wx;

import com.seezoon.infrastructure.rpc.wx.dto.StableAccessTokenResp;
import jakarta.validation.constraints.NotEmpty;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.RestClient;

/**
 * 小程序获取稳定版接口调用凭据
 *
 * @see <a
 *         href="https://developers.weixin.qq.com/miniprogram/dev/OpenApiDoc/mp-access-token/getStableAccessToken.html">...</a>
 */
@RequiredArgsConstructor
@Slf4j
@Component
@Validated
public class StableAccessTokenService {

    private static final String apiUrl = "https://api.weixin.qq.com/cgi-bin/stable_token";

    private final RestClient restClient;

    public StableAccessTokenResp execute(@NotEmpty String appId, @NotEmpty String secret) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("grant_type", "client_credential");
        requestBody.put("appid", appId);
        requestBody.put("secret", secret);
        requestBody.put("force_refresh", false);
        if (log.isDebugEnabled()) {
            log.debug("call stable_token param:{}", requestBody);
        }
        StableAccessTokenResp resp = restClient.post()
                .uri(apiUrl)
                .body(requestBody)
                .retrieve()
                .body(StableAccessTokenResp.class);

        if (log.isDebugEnabled()) {
            log.debug("call stable_token resp:{}", resp);
        }

        return resp;
    }
} 