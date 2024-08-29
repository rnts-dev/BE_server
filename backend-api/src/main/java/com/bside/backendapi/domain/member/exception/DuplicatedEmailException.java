package com.bside.backendapi.domain.member.exception;

import com.bside.backendapi.global.error.exception.BusinessException;
import com.bside.backendapi.global.error.exception.ErrorCode;

public class DuplicatedEmailException extends BusinessException {

    public DuplicatedEmailException(ErrorCode errorCode) {
        super(errorCode);
    }
}
