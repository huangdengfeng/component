package com.seezoon.application.sys.authentication.executor;

import com.seezoon.application.sys.authentication.dto.RefreshTokenCmd;
import com.seezoon.application.sys.authentication.dto.clientobject.AccessTokenCO;
import com.seezoon.domain.dao.mapper.SysUserMapper;
import com.seezoon.domain.dao.po.SysUserPO;
import com.seezoon.domain.service.sys.authentication.LoginTokenService;
import com.seezoon.domain.service.sys.authentication.valueobj.TokenInfoVO;
import com.seezoon.domain.service.sys.valueobj.UserStatusVO;
import com.seezoon.infrastructure.dto.Response;
import com.seezoon.infrastructure.error.ErrorCode;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * 刷新令牌
 */
@RequiredArgsConstructor
@Slf4j
@Component
@Validated
public class RefreshTokenCmdExe {

    private final LoginTokenService loginTokenService;
    private final SysUserMapper sysUserMapper;

    public Response<AccessTokenCO> execute(@NotNull @Valid RefreshTokenCmd cmd) {
        TokenInfoVO tokenInfo = loginTokenService.getTokenInfo(cmd.getRefreshToken());
        // 非法或者失效
        if (tokenInfo == null || StringUtils.isEmpty(tokenInfo.getSubject())) {
            return Response.error(ErrorCode.INVALID_REFRESH_TOKEN.code(), ErrorCode.INVALID_REFRESH_TOKEN.msg());
        }

        // 验证刷新令牌
        Integer userId = Integer.valueOf(tokenInfo.getSubject());

        // 获取用户信息
        SysUserPO sysUserPO = sysUserMapper.selectByPrimaryKey(userId);
        if (null == sysUserPO) {
            return Response.error(ErrorCode.USER_NOT_EXISTS.code(), ErrorCode.USER_NOT_EXISTS.msg());
        }
        // check token info
        if (!loginTokenService.validateCheckSum(tokenInfo.getCheckSum(), tokenInfo.getSubject(),
                sysUserPO.getSecretKey())) {
            log.error("refresh token checksum not match,userId:{}", userId);
            return Response.error(ErrorCode.INVALID_REFRESH_TOKEN.code(), ErrorCode.INVALID_REFRESH_TOKEN.msg());
        }
        // 检查用户状态
        Byte userStatus = sysUserPO.getStatus();
        if (UserStatusVO.inValid(userStatus)) {
            return Response.error(ErrorCode.USER_STATUS_INVALID.code(), ErrorCode.USER_STATUS_INVALID.msg());
        }
        if (UserStatusVO.isLocked(userStatus)) {
            return Response.error(ErrorCode.USER_STATUS_LOCKED.code(), ErrorCode.USER_STATUS_LOCKED.msg());
        }

        // 生成新的令牌
        String accessToken = loginTokenService.createAccessToken(sysUserPO.getUid());
        // 构建返回对象
        AccessTokenCO co = new AccessTokenCO();
        co.setAccessToken(accessToken);
        co.setAccessTokenExpiresIn(loginTokenService.getAccessTokenExpireIn().toSeconds());
        return Response.success(co);
    }
}