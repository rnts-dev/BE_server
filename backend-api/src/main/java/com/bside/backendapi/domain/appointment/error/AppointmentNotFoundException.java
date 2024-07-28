package com.bside.backendapi.domain.appointment.error;

import com.bside.backendapi.global.error.exception.EntityNotFoundException;
import com.bside.backendapi.global.error.exception.ErrorCode;

public class AppointmentNotFoundException extends EntityNotFoundException {

    public AppointmentNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
