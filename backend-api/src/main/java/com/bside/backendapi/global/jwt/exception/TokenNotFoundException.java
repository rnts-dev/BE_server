package com.bside.backendapi.global.jwt.exception;

import com.bside.backendapi.global.error.exception.BusinessException;
import com.bside.backendapi.global.error.exception.ErrorCode;

public class TokenNotFoundException extends BusinessException {
    public TokenNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
