package com.seezoon.infrastructure.rpc.wx.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class Code2SessionResp extends WxError {

    @JsonProperty("session_key")
    private String sessionKey;
    private String unionid;
    private String openid;
}

