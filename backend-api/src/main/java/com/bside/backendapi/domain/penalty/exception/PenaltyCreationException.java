package com.bside.backendapi.domain.penalty.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PenaltyCreationException extends RuntimeException {
    public PenaltyCreationException(String message) {
        super(message);
    }
}