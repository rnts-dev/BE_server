package com.bside.backendapi.domain.member.exception;

import com.bside.backendapi.global.error.exception.ErrorCode;
import jakarta.persistence.EntityNotFoundException;

public class MemberNotFoundException extends EntityNotFoundException {

    public MemberNotFoundException(final ErrorCode errorCode) {
        super(String.valueOf(errorCode));
    }
}