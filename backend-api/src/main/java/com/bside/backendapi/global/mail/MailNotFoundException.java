package com.bside.backendapi.global.mail;

import com.bside.backendapi.global.error.exception.ErrorCode;
import jakarta.persistence.EntityNotFoundException;

public class MailNotFoundException extends EntityNotFoundException {

    public MailNotFoundException(ErrorCode errorCode) {
        super(String.valueOf(errorCode));
    }
}
