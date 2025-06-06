package com.seezoon.application.sys.authentication.executor;

import com.seezoon.application.sys.authentication.dto.UserPasswdLoginCmd;
import com.seezoon.application.sys.authentication.dto.clientobject.AuthorizationTokenCO;
import com.seezoon.domain.dao.mapper.SysUserMapper;
import com.seezoon.domain.dao.po.SysUserPO;
import com.seezoon.domain.service.sys.ModifyUserService;
import com.seezoon.domain.service.sys.authentication.LoginTokenService;
import com.seezoon.domain.service.sys.authentication.UserPasswdVerifyService;
import com.seezoon.domain.service.sys.valueobj.UserStatusVO;
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
 *
 * @author huangdengfeng
 * @date 2022/10/12 12:50
 */
@RequiredArgsConstructor
@Slf4j
@Component
@Validated
public class UserPasswdLoginCmdExe {

    private final LoginTokenService loginTokenService;
    private final UserPasswdVerifyService userPasswdVerifyService;
    private final ModifyUserService modifyUserService;
    private final SysUserMapper sysUserMapper;

    public Response<AuthorizationTokenCO> execute(@NotNull @Valid UserPasswdLoginCmd cmd) {
        // 验密
        boolean verified = userPasswdVerifyService.verify(cmd.getUsername(), cmd.getPassword());
        if (!verified) {
            return Response.error(ErrorCode.USER_PASSWD_WRONG.code(), ErrorCode.USER_PASSWD_WRONG.msg());
        }
        SysUserPO sysUserPO = sysUserMapper.selectByUserName(cmd.getUsername());
        Byte userStatus = sysUserPO.getStatus();

        if (UserStatusVO.inValid(userStatus)) {
            return Response.error(ErrorCode.USER_STATUS_INVALID.code(), ErrorCode.USER_STATUS_INVALID.msg());
        }
        if (UserStatusVO.isLocked(userStatus)) {
            return Response.error(ErrorCode.USER_STATUS_LOCKED.code(), ErrorCode.USER_STATUS_LOCKED.msg());
        }
        // 登录后重置用户安全Key
        Integer uid = sysUserPO.getUid();
        String secretKey = modifyUserService.modifySecretKey(uid, uid);

        String accessToken = loginTokenService.createAccessToken(uid);
        String refreshToken = loginTokenService.createRefreshToken(uid, secretKey);
        AuthorizationTokenCO co = new AuthorizationTokenCO();
        co.setAccessToken(accessToken);
        co.setRefreshToken(refreshToken);
        co.setAccessTokenExpiresIn(loginTokenService.getAccessTokenExpireIn().toSeconds());
        co.setRefreshTokenExpiresIn(loginTokenService.getRefreshTokenExpireIn().toSeconds());
        return Response.success(co);
    }
}
