package com.bside.backendapi.domain.appointment.exception;

import com.bside.backendapi.global.error.exception.CustomException;
import com.bside.backendapi.global.error.exception.ErrorCode;

public class AppointmentException extends CustomException {

    public AppointmentException() {
        super();
    }

    public AppointmentException(String message) {
        super(message);
    }

    public AppointmentException(String message, Throwable cause) {
        super(message, cause);
    }

    public AppointmentException(ErrorCode errorCode) {
        super(errorCode);
    }

    public AppointmentException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }
}
