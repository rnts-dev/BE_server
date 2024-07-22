package com.bside.backendapi.domain.member.error;

import com.bside.backendapi.global.error.exception.BusinessException;
import com.bside.backendapi.global.error.exception.ErrorCode;

public class DismatchPasswordException extends BusinessException {

    public DismatchPasswordException(ErrorCode errorCode) {
        super(errorCode);
    }
}
