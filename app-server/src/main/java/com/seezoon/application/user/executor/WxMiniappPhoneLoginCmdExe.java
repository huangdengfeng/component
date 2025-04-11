package com.seezoon.application.user.executor;

import com.seezoon.application.user.dto.WxMiniappPhoneLoginCmd;
import com.seezoon.application.user.dto.clientobject.WxMiniappPhoneLoginCO;
import com.seezoon.domain.service.LoginService;
import com.seezoon.domain.valueobj.LoginVO;
import com.seezoon.infrastructure.dto.Response;
import com.seezoon.infrastructure.error.ErrorCode;
import com.seezoon.infrastructure.properties.AppProperties;
import com.seezoon.infrastructure.properties.WxProperties;
import com.seezoon.infrastructure.rpc.wx.Code2SessionService;
import com.seezoon.infrastructure.rpc.wx.GetPhoneNumberService;
import com.seezoon.infrastructure.rpc.wx.StableAccessTokenService;
import com.seezoon.infrastructure.rpc.wx.dto.Code2SessionResp;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * 微信小程序手机号快捷登录
 */
@Component
@RequiredArgsConstructor
@Slf4j
@Validated
public class WxMiniappPhoneLoginCmdExe {

    private final LoginService loginService;
    private final AppProperties appProperties;
    private final Code2SessionService code2SessionService;
    private final GetPhoneNumberService getPhoneNumberService;
    private final StableAccessTokenService stableAccessTokenService;

    public Response<WxMiniappPhoneLoginCO> execute(@Valid @NotNull WxMiniappPhoneLoginCmd cmd) {
        WxProperties wx = appProperties.getWx();
        Code2SessionResp resp = code2SessionService.execute(wx.getMiniAppId(), wx.getMinAppSecret(), cmd.getCode());
        if (!resp.success()) {
            log.error("call wx code2session error [{}] [{}]", resp.getErrcode(), resp.getErrmsg());
            return Response.error(ErrorCode.WX_ERROR.code(), ErrorCode.WX_ERROR.msg());
        }

        String accessToken = stableAccessTokenService.execute(appProperties.getWx().getMiniAppId(),
                appProperties.getWx().getMinAppSecret());
        // 获取手机号
        String phoneNumber = getPhoneNumberService.execute(accessToken, cmd.getCode());

        LoginVO loginVO = loginService.loginByOauthMobile(com.seezoon.domain.valueobj.OauthType.WX_MINI_PROGRAM,
                resp.getOpenid(), resp.getUnionid(), phoneNumber);

        return Response.success(new WxMiniappPhoneLoginCO(loginVO.getToken(), loginVO.getExpire()));
    }
} 