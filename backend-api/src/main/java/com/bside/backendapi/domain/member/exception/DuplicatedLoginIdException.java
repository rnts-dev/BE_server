package com.bside.backendapi.domain.member.exception;

import com.bside.backendapi.global.error.exception.BusinessException;
import com.bside.backendapi.global.error.exception.ErrorCode;

public class DuplicatedLoginIdException extends BusinessException {

    public DuplicatedLoginIdException(ErrorCode errorCode) {
        super(errorCode);
    }
}