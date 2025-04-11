package com.seezoon.application.user.executor;

import com.seezoon.application.user.dto.BindWxPhoneNumberCmd;
import com.seezoon.domain.service.UserProfileService;
import com.seezoon.infrastructure.configuration.context.SecurityContextHolder;
import com.seezoon.infrastructure.dto.Response;
import com.seezoon.infrastructure.exception.Assertion;
import com.seezoon.infrastructure.properties.AppProperties;
import com.seezoon.infrastructure.rpc.wx.GetPhoneNumberService;
import com.seezoon.infrastructure.rpc.wx.StableAccessTokenService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@RequiredArgsConstructor
@Slf4j
@Component
@Validated
public class BindWxPhoneNumberCmdExe {

    private final GetPhoneNumberService getPhoneNumberService;
    private final StableAccessTokenService stableAccessTokenService;
    private final UserProfileService userProfileService;
    private final AppProperties appProperties;

    public Response execute(@Valid @NotNull BindWxPhoneNumberCmd cmd) {
        // 获取当前用户ID
        Long uid = SecurityContextHolder.getUid();
        Assertion.notNull(uid);
        String accessToken = stableAccessTokenService.execute(appProperties.getWx().getMiniAppId(),
                appProperties.getWx().getMinAppSecret());
        // 获取手机号
        String phoneNumber = getPhoneNumberService.execute(accessToken, cmd.getCode());
        // 更新用户手机号
        userProfileService.updateMobile(uid, phoneNumber);
        return Response.success();
    }
} 