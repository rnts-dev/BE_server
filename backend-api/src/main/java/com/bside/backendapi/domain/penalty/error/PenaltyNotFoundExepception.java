package com.bside.backendapi.domain.penalty.error;

import com.bside.backendapi.global.error.exception.BusinessException;
import com.bside.backendapi.global.error.exception.ErrorCode;


public class PenaltyNotFoundExepception extends BusinessException {

    public PenaltyNotFoundExepception(ErrorCode errorCode){
        super(errorCode);
    }
}
