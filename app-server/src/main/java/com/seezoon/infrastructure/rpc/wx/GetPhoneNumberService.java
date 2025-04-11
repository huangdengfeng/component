package com.seezoon.infrastructure.rpc.wx;

import com.seezoon.infrastructure.error.ErrorCode;
import com.seezoon.infrastructure.exception.ExceptionFactory;
import com.seezoon.infrastructure.rpc.wx.dto.GetPhoneNumberResp;
import jakarta.validation.constraints.NotEmpty;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.RestClient;

/**
 * 获取小程序用户手机号
 *
 * @see <a
 *         href="https://developers.weixin.qq.com/miniprogram/dev/OpenApiDoc/user-info/phone-number/getPhoneNumber.html">...</a>
 */
@RequiredArgsConstructor
@Slf4j
@Component
@Validated
public class GetPhoneNumberService {

    private static final String apiUrl = "https://api.weixin.qq.com/wxa/business/getuserphonenumber";

    private final RestClient restClient;

    public String execute(@NotEmpty String accessToken, @NotEmpty String code) {
        GetPhoneNumberResp resp = this.execute0(accessToken, code);

        if (!resp.success()) {
            log.error("call wx getPhoneNumber error [{}] [{}]", resp.getErrcode(), resp.getErrmsg());
            throw ExceptionFactory.bizException(ErrorCode.WX_ERROR);
        }
        return resp.getPhoneInfo().getPurePhoneNumber();
    }

    public GetPhoneNumberResp execute0(@NotEmpty String accessToken, @NotEmpty String code) {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("code", code);

        if (log.isDebugEnabled()) {
            log.debug("call getPhoneNumber param:{}", requestBody);
        }

        GetPhoneNumberResp resp = restClient.post()
                .uri(apiUrl + "?access_token=" + accessToken)
                .body(requestBody)
                .retrieve()
                .body(GetPhoneNumberResp.class);

        if (log.isDebugEnabled()) {
            log.debug("call getPhoneNumber resp:{}", resp);
        }

        return resp;
    }
} 