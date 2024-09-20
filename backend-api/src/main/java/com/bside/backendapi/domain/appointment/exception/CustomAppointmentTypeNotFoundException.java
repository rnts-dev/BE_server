package com.bside.backendapi.domain.appointment.exception;

import com.bside.backendapi.global.error.exception.BusinessException;
import com.bside.backendapi.global.error.exception.ErrorCode;

public class CustomAppointmentTypeNotFoundException extends BusinessException {

    public CustomAppointmentTypeNotFoundException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
