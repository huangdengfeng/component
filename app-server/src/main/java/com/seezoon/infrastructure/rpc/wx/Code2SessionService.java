package com.seezoon.infrastructure.rpc.wx;

import com.seezoon.infrastructure.rpc.wx.dto.Code2SessionResp;
import jakarta.validation.constraints.NotEmpty;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * 小程序用户登录
 *
 * @see <a href="https://developers.weixin.qq.com/miniprogram/dev/OpenApiDoc/user-login/code2Session.html">...</a>
 */
@RequiredArgsConstructor
@Slf4j
@Component
@Validated
public class Code2SessionService {

    private static final String apiUrl = "https://api.weixin.qq.com/sns/jscode2session";

    private final RestClient restClient;

    public Code2SessionResp execute(@NotEmpty String appId, @NotEmpty String secret, @NotEmpty String jsCode) {
        URI uri = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParam("appid", appId)
                .queryParam("secret", secret)
                .queryParam("js_code", jsCode)
                .queryParam("grant_type", "authorization_code").build().toUri();
        if (log.isDebugEnabled()) {
            log.debug("call jscode2session param:{}", uri.getQuery());
        }
        Code2SessionResp resp = restClient.get().uri(uri).retrieve().body(Code2SessionResp.class);
        if (log.isDebugEnabled()) {
            log.debug("call jscode2session resp:{}", resp);
        }
        return resp;
    }

}
