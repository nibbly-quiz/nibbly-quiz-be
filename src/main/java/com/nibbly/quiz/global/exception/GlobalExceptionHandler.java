package com.nibbly.quiz.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception exception) {
        log.error("INTERNAL_SERVER_ERROR :: stackTrace = ", exception);

        ErrorResponse data = new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR);
        return ResponseEntity.internalServerError()
                .body(data);
    }

    @ExceptionHandler(NibblyQuizException.class)
    public ResponseEntity<ErrorResponse> handleNibblyQuizException(NibblyQuizException exception) {
        ErrorResponse data = new ErrorResponse(exception.getErrorCode());
        return ResponseEntity.status(exception.getErrorCode().getHttpStatus())
                .body(data);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException exception
    ) {
        String message = getMethodArgumentNotValidMessage(exception);
        ErrorResponse data = new ErrorResponse(message, "INVALID_REQUEST");
        return ResponseEntity.badRequest()
                .body(data);
    }

    private String getMethodArgumentNotValidMessage(MethodArgumentNotValidException exception) {
        return exception.getBindingResult()
                .getAllErrors()
                .get(0)
                .getDefaultMessage();
    }
}
