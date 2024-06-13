package com.bside.backendapi.domain.penalty.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PenaltyCreationException extends RuntimeException {
    public PenaltyCreationException(String message) {
        super(message);
    }
}