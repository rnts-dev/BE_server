package com.bside.backendapi.domain.member.exception;

import com.bside.backendapi.global.error.exception.BusinessException;
import com.bside.backendapi.global.error.exception.ErrorCode;

public class DuplicatedPasswordException extends BusinessException {

    public DuplicatedPasswordException(final ErrorCode errorCode) {
        super(errorCode);
    }
}