package com.bside.backendapi.domain.penalty.error;

import com.bside.backendapi.global.error.exception.ErrorCode;
import jakarta.persistence.EntityNotFoundException;


public class PenaltyNotFoundExepception extends EntityNotFoundException {
    public PenaltyNotFoundExepception(ErrorCode errorCode){
        super(String.valueOf(errorCode));
    }
}
