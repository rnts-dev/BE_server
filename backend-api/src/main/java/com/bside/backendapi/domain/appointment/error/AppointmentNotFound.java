package com.bside.backendapi.domain.appointment.error;

import com.bside.backendapi.global.error.exception.EntityNotFoundException;
import com.bside.backendapi.global.error.exception.ErrorCode;

public class AppointmentNotFound extends EntityNotFoundException {
    public AppointmentNotFound(ErrorCode errorCode){
        super(errorCode);
    }
}
