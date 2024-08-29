package com.bside.backendapi.domain.penalty.error;

import com.bside.backendapi.global.error.exception.ErrorCode;
import jakarta.persistence.EntityNotFoundException;

public class ReceivedPenaltySaveException extends EntityNotFoundException {
    public ReceivedPenaltySaveException(ErrorCode errorCode){
        super(String.valueOf(errorCode));
    }
}
