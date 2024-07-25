package com.bside.backendapi.domain.member.error;

import com.bside.backendapi.global.error.exception.BusinessException;
import com.bside.backendapi.global.error.exception.ErrorCode;

public class EmailNotFoundException extends BusinessException {

    public EmailNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
