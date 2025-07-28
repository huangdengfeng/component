package com.seezoon.domain.service.student.vo;

import com.seezoon.infrastructure.exception.Assertion;

public class StudentSexVO {

    /**
     * 男性
     */
    public static final Byte SEX_MALE = 1;
    /**
     * 女性
     */
    public static final Byte SEX_FEMALE = 2;

    public static void check(Byte code) {
        Assertion.isTrue(SEX_MALE == code || SEX_FEMALE == code, "student sex code is invalid: " + code);
    }
}
