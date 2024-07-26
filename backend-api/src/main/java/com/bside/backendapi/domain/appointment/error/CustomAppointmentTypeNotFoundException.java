package com.bside.backendapi.domain.appointment.error;

import com.bside.backendapi.global.error.exception.BusinessException;
import com.bside.backendapi.global.error.exception.EntityNotFoundException;
import com.bside.backendapi.global.error.exception.ErrorCode;

public class CustomAppointmentTypeNotFoundException extends EntityNotFoundException {

    public CustomAppointmentTypeNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
