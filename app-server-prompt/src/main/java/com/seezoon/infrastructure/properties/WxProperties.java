package com.seezoon.infrastructure.properties;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WxProperties {

    /**
     * 小程序appId
     */
    @NotEmpty
    private String miniAppId;
    /**
     * 小程序appSecret
     */
    @NotEmpty
    private String minAppSecret;
    
    /**
     * 公众号appId
     */
    @NotEmpty
    private String publicAppId;
    
    /**
     * 公众号appSecret
     */
    @NotEmpty
    private String publicAppSecret;
}
