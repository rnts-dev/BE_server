package com.bside.backendapi.domain.penalty.exception;

import com.bside.backendapi.global.error.exception.CustomException;
import com.bside.backendapi.global.error.exception.ErrorCode;

public class PenaltyException extends CustomException {

    public PenaltyException() {
        super();
    }

    public PenaltyException(String message) {
        super(message);
    }

    public PenaltyException(String message, Throwable cause) {
        super(message, cause);
    }

    public PenaltyException(ErrorCode errorCode) {
        super(errorCode);
    }

    public PenaltyException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }
}
