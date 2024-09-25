package com.bside.backendapi.global.mail.exception;

import com.bside.backendapi.global.error.exception.CustomException;
import com.bside.backendapi.global.error.exception.ErrorCode;

public class MailException extends CustomException {

    public MailException() {
        super();
    }

    public MailException(String message) {
        super(message);
    }

    public MailException(String message, Throwable cause) {
        super(message, cause);
    }

    public MailException(ErrorCode errorCode) {
        super(errorCode);
    }

    public MailException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }
}
