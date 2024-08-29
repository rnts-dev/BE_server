package com.bside.backendapi.domain.appointment.exception;

import com.bside.backendapi.global.error.exception.ErrorCode;
import jakarta.persistence.EntityNotFoundException;

public class AppointmentNotFoundException extends EntityNotFoundException {

    public AppointmentNotFoundException(ErrorCode errorCode) {
        super(String.valueOf(errorCode));
    }
}
