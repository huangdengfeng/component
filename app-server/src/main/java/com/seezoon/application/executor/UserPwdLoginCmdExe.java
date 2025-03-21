package com.seezoon.application.executor;

import com.seezoon.application.dto.UserPwdLoginCmd;
import com.seezoon.application.dto.clientobject.UserPwdLoginCO;
import com.seezoon.domain.dao.mapper.UserMapper;
import com.seezoon.domain.dao.po.UserPO;
import com.seezoon.domain.service.UserPasswdVerifyService;
import com.seezoon.domain.valueobj.UserStatus;
import com.seezoon.infrastructure.dto.Response;
import com.seezoon.infrastructure.error.ErrorCode;
import com.seezoon.infrastructure.properties.AppProperties;
import com.seezoon.infrastructure.properties.LoginProperties;
import com.seezoon.infrastructure.security.JwtService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * 账号密码登录
 */
@RequiredArgsConstructor
@Slf4j
@Component
@Validated
public class UserPwdLoginCmdExe {

    private final UserPasswdVerifyService userPasswdVerifyService;
    private final AppProperties appProperties;
    private final JwtService jwtService;
    private final UserMapper userMapper;


    public Response<UserPwdLoginCO> execute(@Valid @NotNull UserPwdLoginCmd cmd) {
        boolean verified = userPasswdVerifyService.verify(cmd.getUsername(), cmd.getPassword());
        if (!verified) {
            return Response.error(ErrorCode.USER_PWD_ERROR.code(), ErrorCode.USER_PWD_ERROR.msg());
        }
        UserPO po = userMapper.selectByUsername(cmd.getUsername());
        Byte userStatus = po.getStatus();
        if (UserStatus.inValid(userStatus)) {
            return Response.error(ErrorCode.USER_STATUS_INVALID.code(), ErrorCode.USER_STATUS_INVALID.msg());
        }
        if (UserStatus.isLocked(userStatus)) {
            return Response.error(ErrorCode.USER_STATUS_LOCKED.code(), ErrorCode.USER_STATUS_LOCKED.msg());
        }
        LoginProperties login = appProperties.getLogin();
        String token = jwtService.sign(String.valueOf(po.getUid()), login.getAccessTokenExpire());
        UserPwdLoginCO co = new UserPwdLoginCO(token, login.getAccessTokenExpire().toSeconds());
        return Response.success(co);
    }
}
