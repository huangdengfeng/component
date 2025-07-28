package com.seezoon.infrastructure.security;

import com.seezoon.infrastructure.properties.AppProperties;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@RequiredArgsConstructor
@Slf4j
@Component
@Validated
public class TokenService implements InitializingBean {

    private final AppProperties appProperties;

    private JwtFactory jwtFactory;

    public String sign(@NotEmpty String subject, @NotNull Duration timeout) {
        return jwtFactory.create(subject, timeout);
    }

    public String verify(@NotEmpty String token) {
        String subject = jwtFactory.getSubject(token);
        return subject;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        jwtFactory = new JwtFactory(appProperties.getLogin().getSecretKey());
    }
}
