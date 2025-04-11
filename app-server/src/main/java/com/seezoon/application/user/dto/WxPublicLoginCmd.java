package com.seezoon.application.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

/**
 * 微信公众号登录
 */
@Getter
@Setter
public class WxPublicLoginCmd {

    @NotEmpty
    @Schema(title = "微信授权码")
    private String code;
} 