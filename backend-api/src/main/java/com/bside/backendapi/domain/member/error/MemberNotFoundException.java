package com.bside.backendapi.domain.member.error;

import com.bside.backendapi.global.error.exception.BusinessException;
import com.bside.backendapi.global.error.exception.ErrorCode;

public class MemberNotFoundException extends BusinessException {

    public MemberNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
