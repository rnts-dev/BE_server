package com.bside.backendapi.domain.member.error;

import com.bside.backendapi.global.error.exception.EntityNotFoundException;
import com.bside.backendapi.global.error.exception.ErrorCode;

public class MemberNotFoundException extends EntityNotFoundException {

    public MemberNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
