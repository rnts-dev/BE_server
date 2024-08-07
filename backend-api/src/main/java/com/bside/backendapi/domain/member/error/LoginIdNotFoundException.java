package com.bside.backendapi.domain.member.error;

import com.bside.backendapi.global.error.exception.EntityNotFoundException;
import com.bside.backendapi.global.error.exception.ErrorCode;

public class LoginIdNotFoundException extends EntityNotFoundException {

    public LoginIdNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
