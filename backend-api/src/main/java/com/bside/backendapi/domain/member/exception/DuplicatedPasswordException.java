package com.zerob.my_rnts.domain.member.exception;

import com.zerob.my_rnts.global.exception.BusinessException;
import com.zerob.my_rnts.global.exception.ErrorCode;

public class DuplicatedPasswordException extends BusinessException {

    public DuplicatedPasswordException(final ErrorCode errorCode) {
        super(errorCode);
    }
}