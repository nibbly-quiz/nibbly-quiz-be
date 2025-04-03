package com.nibbly.global.exception;

import lombok.Getter;

@Getter
public class NibblyQuizException extends RuntimeException {

    private final ErrorCode errorCode;

    public NibblyQuizException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
