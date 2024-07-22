package com.bside.backendapi.domain.penalty.error;

import com.bside.backendapi.global.error.exception.EntityNotFoundException;
import com.bside.backendapi.global.error.exception.ErrorCode;


public class PenaltyNotFoundExepception extends EntityNotFoundException {
    public PenaltyNotFoundExepception(ErrorCode errorCode){
        super(errorCode);
    }
}
