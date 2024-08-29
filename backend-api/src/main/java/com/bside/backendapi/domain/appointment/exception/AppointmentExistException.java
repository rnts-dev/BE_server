package com.bside.backendapi.domain.appointment.exception;

import com.bside.backendapi.global.error.exception.BusinessException;
import com.bside.backendapi.global.error.exception.ErrorCode;

public class AppointmentExistException extends BusinessException {

    public AppointmentExistException(ErrorCode errorCode) {
        super(errorCode);
    }
}
