package com.seezoon.application.user.executor;

import com.seezoon.application.user.dto.WxPublicLoginCmd;
import com.seezoon.application.user.dto.clientobject.WxPublicLoginCO;
import com.seezoon.domain.service.LoginService;
import com.seezoon.domain.valueobj.LoginVO;
import com.seezoon.domain.valueobj.OauthType;
import com.seezoon.infrastructure.dto.Response;
import com.seezoon.infrastructure.error.ErrorCode;
import com.seezoon.infrastructure.properties.AppProperties;
import com.seezoon.infrastructure.properties.WxProperties;
import com.seezoon.infrastructure.rpc.wx.WxOauthAccessTokenService;
import com.seezoon.infrastructure.rpc.wx.dto.WxOauthAccessTokenResp;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * 微信公众号登录
 */
@RequiredArgsConstructor
@Slf4j
@Component
@Validated
public class WxPublicLoginCmdExe {

    private final WxOauthAccessTokenService wxOauthAccessTokenService;
    private final AppProperties appProperties;
    private final LoginService loginService;

    public Response<WxPublicLoginCO> execute(@Valid @NotNull WxPublicLoginCmd cmd) {
        WxProperties wx = appProperties.getWx();
        WxOauthAccessTokenResp resp = wxOauthAccessTokenService.execute(wx.getPublicAppId(), wx.getPublicAppSecret(), cmd.getCode());
        if (!resp.success()) {
            log.error("call wx oauth2 access_token error [{}] [{}]", resp.getErrcode(), resp.getErrmsg());
            return Response.error(ErrorCode.WX_ERROR.code(), ErrorCode.WX_ERROR.msg());
        }
        if (StringUtils.isEmpty(resp.getOpenid())) {
            log.error("get wx openId is empty code{}", cmd.getCode());
            return Response.error(ErrorCode.WX_ERROR.code(), ErrorCode.WX_ERROR.msg());
        }
        LoginVO loginVO = loginService.loginByOauth(OauthType.WX_PUBLIC_PLATFORM, resp.getOpenid(), resp.getUnionid());
        WxPublicLoginCO co = new WxPublicLoginCO(loginVO.getToken(), loginVO.getExpire());
        return Response.success(co);
    }
} 