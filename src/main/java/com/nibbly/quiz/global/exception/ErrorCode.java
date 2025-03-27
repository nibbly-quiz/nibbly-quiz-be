package com.nibbly.quiz.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    INVALID_SCHEDULE_DATE("과거 일자에 대한 문제는 등록할 수 없습니다", HttpStatus.BAD_REQUEST),
    DUPLICATE_OPTIONS("선지 내용이 중복될 수 없습니다", HttpStatus.BAD_REQUEST),
    NO_CORRECT_ANSWER("정답이 없는 문제는 등록할 수 없습니다", HttpStatus.BAD_REQUEST);

    private final String message;
    private final HttpStatus httpStatus;
}
