package com.seezoon.application.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * 手机号验证码登录
 */
@Getter
@Setter
public class MobileLoginCmd {

    @Schema(title = "手机号")
    @NotEmpty
    @Size(min = 11, max = 11)
    private String mobile;

    @Schema(title = "验证码")
    @NotEmpty
    @Size(min = 6, max = 6)
    private String code;

    @Schema(title = "人机验证码")
    @NotEmpty
    private String captcha;
} 