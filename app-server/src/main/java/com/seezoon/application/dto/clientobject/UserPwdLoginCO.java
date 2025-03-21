package com.seezoon.application.dto.clientobject;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserPwdLoginCO {

    /**
     * 添加header Authorization:Bearer token
     */
    private String token;
    /**
     * 单位秒
     */
    private Long expire;

    public UserPwdLoginCO(String token, Long expire) {
        this.token = token;
        this.expire = expire;
    }


}
