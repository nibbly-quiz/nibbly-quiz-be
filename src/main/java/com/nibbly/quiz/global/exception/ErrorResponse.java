package com.nibbly.quiz.global.exception;

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
