package com.seezoon.application.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BindWxPhoneNumberCmd {

    @NotEmpty
    @Schema(title = "手机号获取凭证", description = "手机号获取凭证")
    private String code;
} 