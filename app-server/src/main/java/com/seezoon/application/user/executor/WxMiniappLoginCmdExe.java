package com.seezoon.application.user.executor;

import com.seezoon.application.user.dto.WxMiniappLoginCmd;
import com.seezoon.application.user.dto.clientobject.WxMiniappLoginCO;
import com.seezoon.domain.service.LoginService;
import com.seezoon.domain.valueobj.LoginVO;
import com.seezoon.domain.valueobj.OauthType;
import com.seezoon.infrastructure.dto.Response;
import com.seezoon.infrastructure.error.ErrorCode;
import com.seezoon.infrastructure.properties.AppProperties;
import com.seezoon.infrastructure.properties.WxProperties;
import com.seezoon.infrastructure.rpc.wx.Code2SessionService;
import com.seezoon.infrastructure.rpc.wx.dto.Code2SessionResp;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * 微信小程序登录
 */
@RequiredArgsConstructor
@Slf4j
@Component
@Validated
public class WxMiniappLoginCmdExe {

    private final Code2SessionService code2SessionService;
    private final AppProperties appProperties;
    private final LoginService loginService;

    public Response<WxMiniappLoginCO> execute(@Valid @NotNull WxMiniappLoginCmd cmd) {
        WxProperties wx = appProperties.getWx();
        Code2SessionResp resp = code2SessionService.execute(wx.getMiniAppId(), wx.getMinAppSecret(), cmd.getCode());
        if (!resp.success()) {
            log.error("call wx code2session error [{}] [{}]", resp.getErrcode(), resp.getErrmsg());
            return Response.error(ErrorCode.WX_ERROR.code(), ErrorCode.WX_ERROR.msg());
        }
        LoginVO loginVO = loginService.loginByOauth(OauthType.WX_MINI_PROGRAM, resp.getOpenid(), resp.getUnionid());
        WxMiniappLoginCO co = new WxMiniappLoginCO(loginVO.getToken(), loginVO.getExpire());
        return Response.success(co);
    }
}
