package com.seezoon.application.user.executor;

import com.seezoon.application.user.dto.MobileLoginCmd;
import com.seezoon.application.user.dto.clientobject.MobileLoginCO;
import com.seezoon.domain.service.LoginService;
import com.seezoon.domain.valueobj.LoginVO;
import com.seezoon.infrastructure.dto.Response;
import com.seezoon.infrastructure.error.ErrorCode;
import com.seezoon.infrastructure.properties.AppProperties;
import com.seezoon.infrastructure.rpc.aliyun.CaptchaVerifyer;
import com.seezoon.infrastructure.security.TotpService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * 手机号验证码登录
 */
@RequiredArgsConstructor
@Slf4j
@Component
@Validated
public class MobileLoginCmdExe {

    private final AppProperties appProperties;
    private final CaptchaVerifyer captchaVerifyer;
    private final TotpService totpService;
    private final LoginService loginService;


    public Response<MobileLoginCO> execute(@Valid @NotNull MobileLoginCmd cmd) {
        // 人机验证
        boolean verify = captchaVerifyer.verify(appProperties.getAliyun().getCaptchaSceneId(), cmd.getCaptcha());
        if (!verify) {
            return Response.error(ErrorCode.CAPTCH_VERIFYER_FAIL.code(), ErrorCode.CAPTCH_VERIFYER_FAIL.msg());
        }

        // 验证短信验证码
        boolean validated = totpService.validateCode(
                appProperties.getLogin().getSecretKey().getBytes(StandardCharsets.UTF_8),
                cmd.getCode());
        if (!validated) {
            return Response.error(ErrorCode.MOBILE_CODE_ERROR.code(), ErrorCode.MOBILE_CODE_ERROR.msg());
        }
        LoginVO loginVO = loginService.loginByMobile(cmd.getMobile());
        MobileLoginCO co = new MobileLoginCO(loginVO.getToken(), loginVO.getExpire());
        return Response.success(co);
    }
} 