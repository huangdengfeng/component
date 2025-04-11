package com.seezoon.application.user.dto.clientobject;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MobileLoginCO {

    /**
     * 添加header Authorization:Bearer token
     */
    private String token;
    /**
     * 单位秒
     */
    private Long expire;

    public MobileLoginCO(String token, Long expire) {
        this.token = token;
        this.expire = expire;
    }
} 