package com.seezoon.domain.valueobj;

import com.seezoon.infrastructure.exception.Assertion;

public enum UserStatus {
    /**
     * 有效
     */
    VALID((byte) 1),
    /**
     * 无效
     */
    INVALID((byte) 2),
    /**
     * 锁定
     */
    LOCKED((byte) 3);

    private byte code;

    UserStatus(byte status) {
        this.code = status;
    }

    public static void check(Byte status) {
        Assertion.notNull(status);
        Assertion.isTrue(status == VALID.code()
                || status == INVALID.code()
                || status == LOCKED.code(), "user status incorrect");
    }

    public static boolean valid(byte status) {
        return status == VALID.code();
    }

    public static boolean inValid(byte status) {
        return status == INVALID.code();
    }

    public static boolean isLocked(byte status) {
        return status == LOCKED.code;
    }

    public byte code() {
        return code;
    }
}
