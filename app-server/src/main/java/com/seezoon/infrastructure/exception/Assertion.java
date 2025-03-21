package com.seezoon.infrastructure.exception;


import com.seezoon.infrastructure.error.ErrorCode;

/**
 * Assertion utility class that assists in validating arguments.
 *
 * @author huangdengfeng
 */
public abstract class Assertion {

    private static final int DEFAULT_CODE = ErrorCode.ASSERTION_ERROR.code();

    public static void isTrue(boolean expression, int code, String msg) {
        if (!expression) {
            throw new BizException(code, msg);
        }
    }

    public static void isTrue(boolean expression, String msg) {
        if (!expression) {
            throw new BizException(DEFAULT_CODE, msg);
        }
    }

    public static void isTrue(boolean expression) {
        isTrue(expression, "[Assertion failed] Must be true");
    }

    public static void affectedOne(int affectedRows) {
        isTrue(affectedRows == 1, "expect affected one,actual " + affectedRows);
    }

    public static void leOneRow(int actual) {
        Assertion.isTrue(actual <= 1, "expect: <= 1 ,actual:" + actual);
    }

    public static void affectedRow(int expect, int actual) {
        Assertion.isTrue(expect == actual, "expect: " + expect + " ,actual:" + actual);
    }

    public static void notNull(Object object, int code, String msg) {
        if (object == null) {
            throw new BizException(code, msg);
        }
    }

    public static void notNull(Object object, String msg) {
        if (object == null) {
            throw new BizException(DEFAULT_CODE, msg);
        }
    }

    public static void notNull(Object object) {
        notNull(object, "[Assertion failed] Must not null");
    }
}
