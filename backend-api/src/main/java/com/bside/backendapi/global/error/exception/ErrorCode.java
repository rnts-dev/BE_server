package com.bside.backendapi.global.error.exception;

public interface ErrorCode {
    String name();
    int getStatus();
    String getCode();
    String getMessage();
    RuntimeException getException();
    RuntimeException getException(Throwable cause);
}