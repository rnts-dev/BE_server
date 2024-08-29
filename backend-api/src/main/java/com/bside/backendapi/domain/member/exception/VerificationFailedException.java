package com.zerob.my_rnts.domain.member.exception;

import com.zerob.my_rnts.global.exception.BusinessException;
import com.zerob.my_rnts.global.exception.ErrorCode;

public class VerificationFailedException extends BusinessException {

    public VerificationFailedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
