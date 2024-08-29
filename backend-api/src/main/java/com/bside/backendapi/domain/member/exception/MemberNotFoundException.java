package com.zerob.my_rnts.domain.member.exception;

import com.zerob.my_rnts.global.exception.ErrorCode;
import jakarta.persistence.EntityNotFoundException;

public class MemberNotFoundException extends EntityNotFoundException {

    public MemberNotFoundException(final ErrorCode errorCode) {
        super(String.valueOf(errorCode));
    }
}