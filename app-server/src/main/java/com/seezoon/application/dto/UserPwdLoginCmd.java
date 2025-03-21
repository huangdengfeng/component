package com.seezoon.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * 账号密码登录
 */
@Getter
@Setter
public class UserPwdLoginCmd {

    @NotEmpty
    @Schema(title = "用户名")
    private String username;
    @NotEmpty
    @Size(min = 6)
    @Schema(title = "密码")
    private String password;
}
