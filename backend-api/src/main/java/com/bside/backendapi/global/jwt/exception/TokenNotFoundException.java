package com.bside.backendapi.global.jwt.error;

import com.bside.backendapi.global.error.exception.BusinessException;
import com.bside.backendapi.global.error.exception.ErrorCode;

public class TokenNotFoundException extends BusinessException {
    public TokenNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
