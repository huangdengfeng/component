package com.seezoon.application.user.dto.clientobject;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WxMiniappPhoneLoginCO {

    /**
     * 添加header Authorization:Bearer token
     */
    @Schema(title = "登录凭证", description = "添加header Authorization:Bearer token")
    private String token;
    /**
     * 单位秒
     */
    @Schema(title = "登录凭证过期时间", description = "单位秒")
    private Long expire;

    public WxMiniappPhoneLoginCO(String token, Long expire) {
        this.token = token;
        this.expire = expire;
    }

}
