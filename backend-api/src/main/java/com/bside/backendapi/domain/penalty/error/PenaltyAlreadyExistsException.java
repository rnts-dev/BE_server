package com.bside.backendapi.domain.penalty.error;

import com.bside.backendapi.global.error.exception.EntityNotFoundException;
import com.bside.backendapi.global.error.exception.ErrorCode;

public class PenaltyAlreadyExistsException extends EntityNotFoundException {
    public PenaltyAlreadyExistsException(ErrorCode errorCode){
        super(errorCode);
    }
}
