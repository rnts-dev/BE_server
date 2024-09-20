package com.bside.backendapi.domain.appointment.exception;

import com.bside.backendapi.global.error.exception.BusinessException;
import com.bside.backendapi.global.error.exception.ErrorCode;

public class AppointmentNotFoundException extends BusinessException {

    public AppointmentNotFoundException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
