package com.bside.backendapi.domain.appointment.exception;

import com.bside.backendapi.global.error.exception.BusinessException;
import com.bside.backendapi.global.error.exception.ErrorCode;

public class DuplicatedCustomAppointmentTypeException extends BusinessException {

    public DuplicatedCustomAppointmentTypeException(ErrorCode errorCode) {
        super(errorCode);
    }
}
