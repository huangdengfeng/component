package com.seezoon.infrastructure.properties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AliyunProperties {

    private String accessKeyId;
    private String accessKeySecret;
    /**
     * 短信服务，测试时候可以关闭，避免浪费
     */
    private boolean smsEnabled = true;
    /**
     * 短信签名
     */
    private String smsSignName;
    /**
     * 短信模板
     */
    private String smsLoginTemplateCode;
    /**
     * 人机验证码服务，测试时候可以关闭，避免浪费
     */
    private boolean captchaEnabled = true;
    /**
     * 场景
     */
    private String captchaSceneId;

}
