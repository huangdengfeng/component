package com.seezoon.application.sys.authentication.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

/**
 * 刷新令牌命令
 */
@Getter
@Setter
public class RefreshTokenCmd {

    @NotEmpty
    @Schema(title = "刷新令牌")
    private String refreshToken;
}