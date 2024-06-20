package com.bside.backendapi.global.error.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    // Common
    METHOD_NOT_ALLOWED(405, "C001", "잘못된 요청입니다."),

    // Member
    PASSWORD_NULL_ERROR(400, "M001", "비밀번호가 입력되지 않았습니다."),
    USER_NOT_FOUND(400, "M002", "회원이 아닙니다."),
    DUPLICATED_EMAIL(400, "M003", "중복된 이메일입니다."),
    DUPLICATED_NICKNAME(400, "M004", "중복된 닉네임입니다."),

    // JWT
    TOKEN_NOT_FOUND(400, "J001", "잘못된 토큰입니다.");


    // APPOINTMENT

    // PENALTY

    private final int status;
    private final String code;
    private final String message;

    ErrorCode(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
