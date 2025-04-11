package com.seezoon.application.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SendMobileLoginCaptchaCmd {

    @Schema(title = "手机号")
    @NotEmpty
    @Size(min = 11, max = 11)
    private String mobile;
    @Schema(title = "人机验证码")
    @NotEmpty
    private String captcha;

}
