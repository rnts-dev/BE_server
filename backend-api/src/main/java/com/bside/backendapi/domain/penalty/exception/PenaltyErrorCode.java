package com.bside.backendapi.domain.penalty.exception;

import com.bside.backendapi.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum PenaltyErrorCode implements ErrorCode {

    // PENALTY
    PENALTY_ALREADY_EXISTS(401, "P001", "패널티가 이미 존재합니다."),
    PENALTY_NOT_FOUND(404, "P002", "패널티가 존재하지 않습니다."),
    RECEIVED_PENALTY_SAVE_ERROR(401, "P003", "패널티가 저장되지 않았습니다.");

    private final int status;
    private final String code;
    private final String message;

    @Override
    public int getStatus() {
        return status;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public RuntimeException getException() {
        return new PenaltyException(this);
    }

    @Override
    public RuntimeException getException(Throwable cause) {
        return new PenaltyException(this, cause);
    }
}
