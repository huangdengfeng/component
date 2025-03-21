package com.seezoon.infrastructure.configuration.interceptor;

import com.seezoon.infrastructure.configuration.context.SecurityContextHolder;
import com.seezoon.infrastructure.security.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;

public class SessionInterceptor implements HandlerInterceptor {

    private static final String PREFIX = "Bearer ";
    private static int PREFIX_LENGTH = PREFIX.length();

    private final JwtService jwtService;

    public SessionInterceptor(JwtService jwtService) {
        this.jwtService = Objects.requireNonNull(jwtService);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.isEmpty(authorization) || !authorization.startsWith(PREFIX)
                || authorization.length() <= PREFIX_LENGTH) {
            // 401 未登录
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }
        String token = authorization.substring(PREFIX_LENGTH);
        String uid = jwtService.verify(token);
        if (StringUtils.isEmpty(uid)) {
            // 401 未登录
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }
        // 放入uid 到上下文
        SecurityContextHolder.setUid(Long.parseLong(uid));
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        SecurityContextHolder.clear();
    }


}
