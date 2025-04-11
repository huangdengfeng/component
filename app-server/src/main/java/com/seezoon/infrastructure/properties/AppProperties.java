package com.seezoon.infrastructure.properties;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.validation.annotation.Validated;

/**
 * 业务应用配置
 *
 * @author huangdengfeng
 * @date 2023/9/8 16:29
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "app")
@Validated
public class AppProperties {

    @NotNull
    @Valid
    @NestedConfigurationProperty
    private LoginProperties login = new LoginProperties();

    @Valid
    @NotNull
    @NestedConfigurationProperty
    private CorsProperties cors = new CorsProperties();

    @Valid
    @NotNull
    @NestedConfigurationProperty
    private WxProperties wx = new WxProperties();

    @Valid
    @NotNull
    @NestedConfigurationProperty
    private AliyunProperties aliyun = new AliyunProperties();
    /**
     * 文件、图片前缀
     */
    private String fileUrlPrefix;
}
