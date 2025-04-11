package com.seezoon.domain.service;

import com.seezoon.domain.dao.mapper.OauthMapper;
import com.seezoon.domain.dao.mapper.UserMapper;
import com.seezoon.domain.dao.mapper.UserProfileMapper;
import com.seezoon.domain.dao.po.OauthPO;
import com.seezoon.domain.dao.po.UserPO;
import com.seezoon.domain.dao.po.UserProfilePO;
import com.seezoon.domain.valueobj.LoginVO;
import com.seezoon.domain.valueobj.OauthType;
import com.seezoon.domain.valueobj.UserStatus;
import com.seezoon.infrastructure.error.ErrorCode;
import com.seezoon.infrastructure.exception.Assertion;
import com.seezoon.infrastructure.exception.ExceptionFactory;
import com.seezoon.infrastructure.properties.AppProperties;
import com.seezoon.infrastructure.security.TokenService;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.Duration;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@RequiredArgsConstructor
@Slf4j
@Service
@Validated
public class LoginService {

    private final TokenService tokenService;
    private final RegistUserService registUserService;
    private final UserProfileService userProfileService;
    private final AppProperties appProperties;
    private final OauthMapper oauthMapper;
    private final UserProfileMapper userProfileMapper;
    private final UserMapper userMapper;


    public LoginVO loginByMobile(@NotEmpty String mobile) {
        UserProfilePO userProfilePO = userProfileMapper.selectByMobile(mobile);
        if (userProfilePO != null) {
            UserPO userPO = userMapper.selectByPrimaryKey(userProfilePO.getUid());
            return this.login(userPO);
        }
        // 注册
        UserPO userPO = registUserService.mobileRegist(mobile);
        return this.login(userPO);
    }

    /**
     * 用户名登录
     *
     * @param userName
     * @return
     */
    @Transactional(readOnly = true)
    public LoginVO loginByUserName(@NotEmpty String userName) {
        UserPO userPO = userMapper.selectByUsername(userName);
        return this.login(userPO);
    }

    /**
     * oauth2 手机号快捷登录
     *
     * @param oauthType
     * @param oauthId
     * @param unionId
     * @param mobile
     * @return
     */
    @Transactional
    public LoginVO loginByOauthMobile(@NotNull OauthType oauthType, @NotEmpty String oauthId, String unionId,
            @NotEmpty String mobile) {
        LoginVO loginVO = this.loginByOauth(oauthType, oauthId, unionId);
        userProfileService.updateMobile(loginVO.getUid(), mobile);
        return loginVO;
    }

    /**
     * oauth2 登录，不存在则自动注册
     *
     * @param oauthType
     * @param oauthId
     * @param unionId
     * @return
     */
    @Transactional
    public LoginVO loginByOauth(@NotNull OauthType oauthType, @NotEmpty String oauthId, String unionId) {
        OauthPO oauthPO = oauthMapper.selectByOauth(oauthType.type(), oauthId);
        if (null != oauthPO) {
            // 更新unionId
            if (StringUtils.isEmpty(oauthPO.getUnionId()) && StringUtils.isNotEmpty(unionId)) {
                oauthPO.setUnionId(unionId);
                oauthPO.setUpdateTime(LocalDateTime.now());
                Assertion.affectedOne(this.oauthMapper.updateByPrimaryKey(oauthPO));
            }
            UserPO userPO = userMapper.selectByPrimaryKey(oauthPO.getUid());
            return this.login(userPO);
        }
        // 注册
        UserPO userPO = registUserService.oauthRegist(oauthType, oauthId, unionId);
        return this.login(userPO);
    }

    private LoginVO login(UserPO userPO) {
        Assertion.notNull(userPO, "user must not null");
        Byte userStatus = userPO.getStatus();
        // 停用
        if (UserStatus.inValid(userStatus)) {
            throw ExceptionFactory.bizException(ErrorCode.USER_STATUS_INVALID);
        }
        // 锁定
        if (UserStatus.isLocked(userStatus)) {
            throw ExceptionFactory.bizException(ErrorCode.USER_STATUS_LOCKED);
        }
        Duration accessTokenExpire = appProperties.getLogin().getAccessTokenExpire();
        String token = tokenService.sign(String.valueOf(userPO.getUid()), accessTokenExpire);
        return new LoginVO(userPO.getUid(), token, accessTokenExpire.toSeconds());
    }
}
