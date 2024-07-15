package com.bside.backendapi.global.error.exception;

public class CustomAccessDeniedException extends BusinessException {

    public CustomAccessDeniedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
