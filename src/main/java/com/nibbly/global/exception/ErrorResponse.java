package com.nibbly.global.exception;

import lombok.Getter;

@Getter
public class ErrorResponse {
    private final String message;
    private final String errorCode;

    public ErrorResponse(String message, String errorCode) {
        this.message = message;
        this.errorCode = errorCode;
    }

    public ErrorResponse(ErrorCode errorCode) {
        this(errorCode.getMessage(), errorCode.name());
    }
}
