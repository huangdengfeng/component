package com.seezoon.infrastructure.configuration.context;

public class SecurityContextHolder {

    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public static Long getUid() {
        return threadLocal.get();
    }

    public static void setUid(Long uid) {
        threadLocal.set(uid);
    }

    public static void clear() {
        threadLocal.remove();
    }
}
