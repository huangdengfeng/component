package com.seezoon.domain.valueobj;

import lombok.Getter;

@Getter
public class LoginVO {

    private Long uid;
    private String token;
    /**
     * 单位秒
     */
    private Long expire;

    public LoginVO(Long uid, String token, Long expire) {
        this.uid = uid;
        this.token = token;
        this.expire = expire;
    }
}
