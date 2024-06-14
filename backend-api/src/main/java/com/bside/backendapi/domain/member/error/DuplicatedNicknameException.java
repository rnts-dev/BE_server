package com.bside.backendapi.domain.member.error;

import com.bside.backendapi.global.error.exception.BusinessException;
import com.bside.backendapi.global.error.exception.ErrorCode;

public class DuplicatedNicknameException extends BusinessException {

    public DuplicatedNicknameException(ErrorCode errorCode) {
        super(errorCode);
    }
}
