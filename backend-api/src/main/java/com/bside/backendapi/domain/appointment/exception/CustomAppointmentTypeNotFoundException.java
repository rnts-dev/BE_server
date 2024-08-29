package com.bside.backendapi.domain.appointment.exception;

import com.bside.backendapi.global.error.exception.ErrorCode;
import jakarta.persistence.EntityNotFoundException;

public class CustomAppointmentTypeNotFoundException extends EntityNotFoundException {

    public CustomAppointmentTypeNotFoundException(final ErrorCode errorCode) {
        super(String.valueOf(errorCode));
    }
}
