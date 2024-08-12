package com.bside.backendapi.domain.penalty.error;

import com.bside.backendapi.global.error.exception.EntityNotFoundException;
import com.bside.backendapi.global.error.exception.ErrorCode;

public class ReceivedPenaltySaveException extends EntityNotFoundException {
    public ReceivedPenaltySaveException(ErrorCode errorCode){
        super(errorCode);
    }
}
