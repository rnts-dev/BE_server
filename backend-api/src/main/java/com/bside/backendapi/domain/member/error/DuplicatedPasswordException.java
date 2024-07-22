package com.bside.backendapi.domain.member.error;

import com.bside.backendapi.global.error.exception.BusinessException;
import com.bside.backendapi.global.error.exception.ErrorCode;

public class DuplicatedPasswordException extends BusinessException {

    public DuplicatedPasswordException(ErrorCode errorCode) {
        super(errorCode);
    }
}
