package com.zerob.my_rnts.domain.member.exception;

import com.zerob.my_rnts.global.exception.BusinessException;
import com.zerob.my_rnts.global.exception.ErrorCode;

public class DuplicatedNicknameException extends BusinessException {

    public DuplicatedNicknameException(final ErrorCode errorCode) {
        super(errorCode);
    }
}