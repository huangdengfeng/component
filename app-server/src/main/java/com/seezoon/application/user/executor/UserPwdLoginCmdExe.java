package com.seezoon.application.user.executor;

import com.seezoon.application.user.dto.UserPwdLoginCmd;
import com.seezoon.application.user.dto.clientobject.UserPwdLoginCO;
import com.seezoon.domain.service.LoginService;
import com.seezoon.domain.service.UserPasswdVerifyService;
import com.seezoon.domain.valueobj.LoginVO;
import com.seezoon.infrastructure.dto.Response;
import com.seezoon.infrastructure.error.ErrorCode;
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
    private final LoginService loginService;

    public Response<UserPwdLoginCO> execute(@Valid @NotNull UserPwdLoginCmd cmd) {
        boolean verified = userPasswdVerifyService.verify(cmd.getUsername(), cmd.getPassword());
        if (!verified) {
            return Response.error(ErrorCode.USER_PWD_ERROR.code(), ErrorCode.USER_PWD_ERROR.msg());
        }
        LoginVO loginVO = loginService.loginByUserName(cmd.getUsername());
        UserPwdLoginCO co = new UserPwdLoginCO(loginVO.getToken(), loginVO.getExpire());
        return Response.success(co);
    }
}
