package com.seezoon.infrastructure.rpc.aliyun;

import com.seezoon.infrastructure.error.ErrorCode;
import com.seezoon.infrastructure.exception.SysException;
import com.seezoon.infrastructure.properties.AliyunProperties;
import com.seezoon.infrastructure.properties.AppProperties;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.HttpStatus;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * 阿里云验证码服务
 */
@RequiredArgsConstructor
@Slf4j
@Component
@Validated
public class CaptchaVerifyer implements InitializingBean {

    private final String endpoint = "captcha.cn-shanghai.aliyuncs.com";
    private final Integer readTimeout = 6000;
    private final Integer connectTimeout = 3000;

    private final AppProperties appProperties;
    private com.aliyun.captcha20230305.Client client;

    /**
     * @param sceneId 本次验证的场景ID
     * @param captcha 前端传来的验证参数 CaptchaVerifyParam
     * @return
     */
    public boolean verify(@NotEmpty String sceneId, @NotEmpty String captcha) {
        if (!appProperties.getAliyun().isCaptchaEnabled()) {
            log.warn("CaptchaVerifyer disabled sceneId:{}", sceneId);
            return true;
        }
        com.aliyun.captcha20230305.models.VerifyIntelligentCaptchaRequest request = new com.aliyun.captcha20230305.models.VerifyIntelligentCaptchaRequest();
        request.sceneId = sceneId;
        request.captchaVerifyParam = captcha;
        try {
            com.aliyun.captcha20230305.models.VerifyIntelligentCaptchaResponse resp = client.verifyIntelligentCaptcha(
                    request);
            if (resp.statusCode != HttpStatus.SC_OK) {
                log.error("CaptchaVerifyer error due to http status {}", resp.statusCode);
                throw new SysException(ErrorCode.CAPTCH_VERIFYER_ERROR.code(), ErrorCode.CAPTCH_VERIFYER_ERROR.msg());
            }
            if (!resp.getBody().getSuccess()) {
                log.error("CaptchaVerifyer error {} {},sceneId:{}", resp.getBody().getCode(),
                        resp.getBody().getMessage(), sceneId);
                throw new SysException(ErrorCode.CAPTCH_VERIFYER_ERROR.code(), ErrorCode.CAPTCH_VERIFYER_ERROR.msg());
            }
            Boolean captchaVerifyResult = resp.body.result.verifyResult;
            // 原因code
            String captchaVerifyCode = resp.body.result.verifyCode;
            if (!captchaVerifyResult) {
                log.error("CaptchaVerifyer not pass {},sceneId:{}", captchaVerifyCode, sceneId);
            }
            return captchaVerifyResult;
        } catch (SysException e) {
            throw e;
        } catch (Exception e) {
            log.error("CaptchaVerifyer error {}, sceneId:{}", e.getMessage(), sceneId);
            throw new SysException(ErrorCode.CAPTCH_VERIFYER_ERROR.code(), ErrorCode.CAPTCH_VERIFYER_ERROR.msg());
        }
    }

    private com.aliyun.captcha20230305.Client createClient() throws Exception {
        AliyunProperties aliyun = appProperties.getAliyun();
        String accessKeyId = aliyun.getAccessKeyId();
        String accessKeySecret = aliyun.getAccessKeySecret();
        com.aliyun.teaopenapi.models.Config config = new com.aliyun.teaopenapi.models.Config()
                .setAccessKeyId(accessKeyId)
                .setAccessKeySecret(accessKeySecret);
        config.endpoint = endpoint;
        config.setReadTimeout(readTimeout);
        config.setConnectTimeout(connectTimeout);
        return new com.aliyun.captcha20230305.Client(config);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.client = this.createClient();
    }
}
