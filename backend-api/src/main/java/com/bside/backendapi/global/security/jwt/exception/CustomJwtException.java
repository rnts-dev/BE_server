package com.bside.backendapi.global.security.jwt.exception;

public class CustomJwtException extends RuntimeException {

    public CustomJwtException(String message) {
        super(message);
    }
}
