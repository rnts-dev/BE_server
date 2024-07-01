package com.bside.backendapi.domain.appointment.error;

import com.bside.backendapi.global.error.exception.BusinessException;
import com.bside.backendapi.global.error.exception.ErrorCode;

public class CustomAppointmentTypeNotFoundException extends BusinessException {

    public CustomAppointmentTypeNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
