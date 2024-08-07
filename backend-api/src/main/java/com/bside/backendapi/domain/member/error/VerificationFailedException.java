package com.bside.backendapi.domain.member.error;

import com.bside.backendapi.global.error.exception.BusinessException;
import com.bside.backendapi.global.error.exception.ErrorCode;

public class VerificationFailedException extends BusinessException {

    public VerificationFailedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
