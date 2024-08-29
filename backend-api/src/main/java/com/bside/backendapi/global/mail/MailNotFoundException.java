package com.zerob.my_rnts.global.mail;

import com.zerob.my_rnts.global.exception.ErrorCode;
import jakarta.persistence.EntityNotFoundException;

public class MailNotFoundException extends EntityNotFoundException {

    public MailNotFoundException(ErrorCode errorCode) {
        super(String.valueOf(errorCode));
    }
}
