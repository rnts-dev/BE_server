package com.bside.backendapi.global.error.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
    // Exception은 공통으로 커스텀하여 처리한다.
    private final ErrorCode errorCode;

    public BusinessException(final ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
