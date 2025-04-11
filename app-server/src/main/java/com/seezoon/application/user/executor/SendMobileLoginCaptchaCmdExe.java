package com.seezoon.application.user.executor;

import com.seezoon.application.user.dto.SendMobileLoginCaptchaCmd;
import com.seezoon.infrastructure.dto.Response;
import com.seezoon.infrastructure.error.ErrorCode;
import com.seezoon.infrastructure.properties.AliyunProperties;
import com.seezoon.infrastructure.properties.AppProperties;
import com.seezoon.infrastructure.rpc.aliyun.CaptchaVerifyer;
import com.seezoon.infrastructure.rpc.aliyun.SmsSender;
import com.seezoon.infrastructure.security.TotpService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@RequiredArgsConstructor
@Slf4j
@Component
@Validated
public class SendMobileLoginCaptchaCmdExe {

    private final TotpService totpService;
    private final SmsSender smsSender;
    private final CaptchaVerifyer captchaVerifyer;
    private final AppProperties appProperties;

    public Response execute(@Valid @NotNull SendMobileLoginCaptchaCmd cmd) {
        AliyunProperties aliyun = appProperties.getAliyun();
        // 安全验证
        boolean verify = captchaVerifyer.verify(appProperties.getAliyun().getCaptchaSceneId(), cmd.getCaptcha());
        if (!verify) {
            return Response.error(ErrorCode.CAPTCH_VERIFYER_FAIL.code(), ErrorCode.CAPTCH_VERIFYER_FAIL.msg());
        }
        String secretKey = appProperties.getLogin().getSecretKey();
        String code = totpService.generateCode(secretKey.getBytes(StandardCharsets.UTF_8));
        Map<String, String> map = new HashMap<>();
        map.put("code", code);
        smsSender.sendSms(cmd.getMobile(), aliyun.getSmsSignName(), aliyun.getSmsLoginTemplateCode(), map);
        return Response.success();
    }
}
