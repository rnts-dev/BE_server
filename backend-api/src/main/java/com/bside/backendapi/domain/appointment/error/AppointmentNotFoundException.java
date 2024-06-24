package com.bside.backendapi.domain.appointment.error;

import com.bside.backendapi.global.error.exception.BusinessException;
import com.bside.backendapi.global.error.exception.ErrorCode;

public class AppointmentNotFoundException extends BusinessException {

    public AppointmentNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
