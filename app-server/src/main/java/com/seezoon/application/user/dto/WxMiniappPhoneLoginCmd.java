package com.seezoon.application.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

/**
 * 微信小程序手机号快捷登录
 */
@Getter
@Setter
public class WxMiniappPhoneLoginCmd {

    @NotEmpty
    @Schema(title = "微信code", description = "微信登录code")
    private String code;
    
    @NotEmpty
    @Schema(title = "手机号获取凭证", description = "手机号获取凭证")
    private String phoneCode;
} 