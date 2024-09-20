package com.bside.backendapi.global.mail;

import com.bside.backendapi.global.error.exception.BusinessException;
import com.bside.backendapi.global.error.exception.ErrorCode;

public class MailNotFoundException extends BusinessException {

    public MailNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}