package com.seezoon.infrastructure.rpc.aliyun;


import com.seezoon.infrastructure.error.ErrorCode;
import com.seezoon.infrastructure.exception.SysException;
import com.seezoon.infrastructure.properties.AliyunProperties;
import com.seezoon.infrastructure.properties.AppProperties;
import com.seezoon.infrastructure.utils.JsonUtils;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.Map;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.HttpStatus;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * 阿里云短信服务
 */
@RequiredArgsConstructor
@Slf4j
@Component
@Validated
public class SmsSender implements InitializingBean {

    private final String endpoint = "dysmsapi.aliyuncs.com";
    private final Integer readTimeout = 6000;
    private final Integer connectTimeout = 3000;
    private final String successCode = "OK";

    private final AppProperties appProperties;
    private com.aliyun.dysmsapi20170525.Client client;

    /**
     * 发送短信
     *
     * @param phoneNumber 手机号
     * @param signName 短信签名，eg【阿里云短信测试】
     * @param templateCode 短信模板，eg SMS_154950909
     * @param params 短信模板占位符
     */
    public void sendSms(@NotEmpty String phoneNumber, @NotEmpty String signName, @NotEmpty String templateCode,
            @NotNull Map<String, String> params) {
        if (log.isDebugEnabled()) {
            log.debug("sendSms phoneNumber:{},signName:{},templateCode:{}", phoneNumber, signName, templateCode);
        }
        if (!appProperties.getAliyun().isSmsEnabled()) {
            log.warn("sendSms disabled phoneNumber:{},signName:{},templateCode:{}", phoneNumber, signName,
                    templateCode);
            return;
        }
        com.aliyun.dysmsapi20170525.models.SendSmsRequest sendSmsRequest = new com.aliyun.dysmsapi20170525.models.SendSmsRequest()
                .setSignName(signName)
                .setTemplateCode(templateCode)
                .setPhoneNumbers(phoneNumber)
                .setTemplateParam(JsonUtils.toJson(params));
        try {
            com.aliyun.dysmsapi20170525.models.SendSmsResponse resp = client.sendSms(sendSmsRequest);
            if (resp.statusCode != HttpStatus.SC_OK) {
                log.error("sendSms error due to http status {}", resp.statusCode);
                throw new SysException(ErrorCode.SMS_SEND_ERROR.code(), ErrorCode.SMS_SEND_ERROR.msg());
            }
            if (!Objects.equals(successCode, resp.getBody().getCode())) {
                log.error("sendSms error {} {}, phoneNumber:{},signName:{},templateCode:{}", resp.getBody().getCode(),
                        resp.getBody().getMessage(),
                        phoneNumber, signName, templateCode);
                throw new SysException(ErrorCode.SMS_SEND_ERROR.code(), ErrorCode.SMS_SEND_ERROR.msg());
            }
        } catch (SysException e) {
            throw e;
        } catch (Exception e) {
            log.error("sendSms error {}, phoneNumber:{},signName:{},templateCode:{}", e.getMessage(), phoneNumber,
                    signName, templateCode);
            throw new SysException(ErrorCode.SMS_SEND_ERROR.code(), ErrorCode.SMS_SEND_ERROR.msg());
        }
    }

    private com.aliyun.dysmsapi20170525.Client createClient() throws Exception {
        AliyunProperties aliyun = appProperties.getAliyun();
        String accessKeyId = aliyun.getAccessKeyId();
        String accessKeySecret = aliyun.getAccessKeySecret();
        com.aliyun.teaopenapi.models.Config config = new com.aliyun.teaopenapi.models.Config()
                .setAccessKeyId(accessKeyId)
                .setAccessKeySecret(accessKeySecret);
        config.endpoint = endpoint;
        config.setReadTimeout(readTimeout);
        config.setConnectTimeout(connectTimeout);
        return new com.aliyun.dysmsapi20170525.Client(config);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.client = this.createClient();
    }
}
