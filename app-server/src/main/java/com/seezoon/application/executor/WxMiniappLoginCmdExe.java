package com.seezoon.application.executor;

import com.seezoon.application.dto.WxMiniappLoginCmd;
import com.seezoon.application.dto.clientobject.WxMiniappLoginCO;
import com.seezoon.domain.dao.mapper.OauthMapper;
import com.seezoon.domain.dao.mapper.UserMapper;
import com.seezoon.domain.dao.po.OauthPO;
import com.seezoon.domain.dao.po.UserPO;
import com.seezoon.domain.service.RegistUserService;
import com.seezoon.domain.valueobj.OauthType;
import com.seezoon.domain.valueobj.UserStatus;
import com.seezoon.infrastructure.dto.Response;
import com.seezoon.infrastructure.error.ErrorCode;
import com.seezoon.infrastructure.exception.Assertion;
import com.seezoon.infrastructure.properties.AppProperties;
import com.seezoon.infrastructure.properties.LoginProperties;
import com.seezoon.infrastructure.rpc.wx.Code2SessionService;
import com.seezoon.infrastructure.rpc.wx.dto.Code2SessionResp;
import com.seezoon.infrastructure.security.JwtService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
    private final JwtService jwtService;
    private final RegistUserService registUserService;
    private final OauthMapper oauthMapper;
    private final UserMapper userMapper;

    public Response<WxMiniappLoginCO> execute(@Valid @NotNull WxMiniappLoginCmd cmd) {
        LoginProperties login = appProperties.getLogin();
        Code2SessionResp resp = code2SessionService.execute(login.getWxAppId(), login.getWxSecret(), cmd.getCode());
        if (!resp.success()) {
            log.error("call wx code2session error [{}] [{}]", resp.getErrcode(), resp.getErrmsg());
            return Response.error(ErrorCode.WX_ERROR.code(), ErrorCode.WX_ERROR.msg());
        }
        if (StringUtils.isEmpty(resp.getOpenid())) {
            log.error("get wx openId is empty code{}", cmd.getCode());
            return Response.error(ErrorCode.WX_ERROR.code(), ErrorCode.WX_ERROR.msg());
        }
        Long uid = null;
        OauthPO oauthPO = oauthMapper.selectByOauth(OauthType.WX.type(), resp.getOpenid());
        if (null != oauthPO) {
            UserPO userPO = userMapper.selectByPrimaryKey(oauthPO.getUid());
            Assertion.notNull(userPO, "user must not null");
            Byte userStatus = userPO.getStatus();
            if (UserStatus.inValid(userStatus)) {
                return Response.error(ErrorCode.USER_STATUS_INVALID.code(), ErrorCode.USER_STATUS_INVALID.msg());
            }
            if (UserStatus.isLocked(userStatus)) {
                return Response.error(ErrorCode.USER_STATUS_LOCKED.code(), ErrorCode.USER_STATUS_LOCKED.msg());
            }
            uid = oauthPO.getUid();
        } else {
            uid = registUserService.execute(OauthType.WX, resp.getOpenid(), resp.getUnionid());
        }
        Assertion.notNull(uid, "uid must not null");
        String token = jwtService.sign(String.valueOf(uid), login.getAccessTokenExpire());
        WxMiniappLoginCO co = new WxMiniappLoginCO(token, login.getAccessTokenExpire().toSeconds());
        return Response.success(co);
    }
}
