package com.seezoon.application.sys.authentication.dto.clientobject;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * 登录凭据
 *
 * @author huangdengfeng
 * @date 2022/10/12 12:56
 */
@Getter
@Setter
@RequiredArgsConstructor
public class AuthorizationTokenCO {

    @Schema(title = "登录凭证", description = "添加header Authorization:Bearer token")
    private String accessToken;
    @Schema(title = "登录凭证过期(s)", description = "过期时间较短")
    private Long accessTokenExpiresIn;
    @Schema(title = "刷新凭证", description = "调用refresh接口获取最新登录凭证")
    private String refreshToken;
    @Schema(title = "刷新凭证过期(s)", description = "过期时间较长")
    private Long refreshTokenExpiresIn;

}
