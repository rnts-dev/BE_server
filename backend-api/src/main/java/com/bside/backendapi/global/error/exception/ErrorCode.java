package com.bside.backendapi.global.error.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    // Common
    METHOD_NOT_ALLOWED(405, "C001", "잘못된 요청입니다."),
    ACCESS_DENIED(403, "C002", "해당 자원에 대한 접근 권한이 없습니다."),

    // Member
    PASSWORD_NULL_ERROR(400, "M001", "비밀번호가 입력되지 않았습니다."),
    USER_NOT_FOUND(400, "M002", "회원이 아닙니다."),
    DUPLICATED_EMAIL(400, "M003", "중복된 이메일 입니다."),
    DUPLICATED_NICKNAME(400, "M004", "중복된 닉네임 입니다."),
    DUPLICATED_PASSWORD(400, "M005", "기존 비밀번호와 동일합니다."),


    // JWT
    TOKEN_NOT_FOUND(400, "J001", "잘못된 토큰입니다."),



    // APPOINTMENT
    APPOINTMENT_NOT_FOUND(400, "A001", "약속이 없습니다."),
    APPOINTMENT_MISS_MATCH(400, "A002", "일치하는 약속이 없습니다."),
    APPOINTMENT_EXIST(400, "A003", "해당 유형을 사용하는 약속이 있습니다."),
    CUSTOM_TYPE_NOT_FOUND(400, "A004", "사용자가 등록한 약속 유형이 없습니다."),

    // PENALTY
    PENALTY_NOT_FOUND(400,"P001","해당 패널티를 찾을수 없습니다");

    private final int status;
    private final String code;
    private final String message;

    ErrorCode(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
