package com.seezoon.infrastructure.configuration.interceptor;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 打印线程号
 * <p>
 * log pattern use %X{tid:-默认值} or %X{tid}
 * </p>
 *
 * @author huangdengfeng
 * @date 2023/8/27 22:35
 */
public class ThreadIdMdcInterceptor implements HandlerInterceptor {

    private static final String NAME = "tid";
    private static final String REQ_ID = "X-Request-Id";
    private static final int LENGTH = 10;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String requestId = request.getHeader(REQ_ID);
        if (StringUtils.isEmpty(requestId)) {
            requestId = RandomStringUtils.randomAlphanumeric(LENGTH);
        }
        MDC.put(NAME, requestId);
        // 设置到响应
        response.setHeader(REQ_ID, requestId);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
            Exception ex) {

        MDC.remove(NAME);
    }
}
