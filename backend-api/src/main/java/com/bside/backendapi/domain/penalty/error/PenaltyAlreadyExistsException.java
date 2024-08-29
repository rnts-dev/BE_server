package com.bside.backendapi.domain.penalty.error;

import com.bside.backendapi.global.error.exception.ErrorCode;
import jakarta.persistence.EntityNotFoundException;

public class PenaltyAlreadyExistsException extends EntityNotFoundException {
    public PenaltyAlreadyExistsException(ErrorCode errorCode){
        super(String.valueOf(errorCode));
    }
}
