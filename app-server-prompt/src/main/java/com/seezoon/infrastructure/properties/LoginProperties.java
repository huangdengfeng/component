package com.seezoon.infrastructure.properties;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.Duration;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * 登录相关参数
 *
 * @author huangdengfeng
 * @date 2023/9/8 16:30
 */
@Getter
@Setter
public class LoginProperties {

    /**
     * 登录有效期,默认两小时
     */
    @NotNull
    private Duration accessTokenExpire = Duration.ofHours(2);
    /**
     * 登录安全密钥
     */
    @NotEmpty
    @Length(min = 32)
    private String secretKey;
}
