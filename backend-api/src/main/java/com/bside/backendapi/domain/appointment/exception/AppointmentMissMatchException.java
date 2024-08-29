package com.bside.backendapi.domain.appointment.exception;

import com.bside.backendapi.global.error.exception.BusinessException;
import com.bside.backendapi.global.error.exception.ErrorCode;

public class AppointmentMissMatchException extends BusinessException {
    public AppointmentMissMatchException(ErrorCode errorCode) {
        super(errorCode);
    }
}
