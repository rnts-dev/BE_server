package com.bside.backendapi.domain.member.exception;

import com.bside.backendapi.global.error.exception.BusinessException;
import com.bside.backendapi.global.error.exception.ErrorCode;

public class DuplicatedMailException extends BusinessException {

    public DuplicatedMailException(final ErrorCode errorCode) {
        super(errorCode);
    }
}