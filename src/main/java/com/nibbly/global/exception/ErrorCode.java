package com.nibbly.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    INVALID_SCHEDULE_DATE("과거 일자에 대한 문제는 등록할 수 없습니다", HttpStatus.BAD_REQUEST),
    DUPLICATE_OPTIONS("선지 내용이 중복될 수 없습니다", HttpStatus.BAD_REQUEST),
    NO_CORRECT_ANSWER("정답이 없는 문제는 등록할 수 없습니다", HttpStatus.BAD_REQUEST),
    INVALID_OPTION_LENGTH("선지 내용은 100자를 넘을 수 없습니다", HttpStatus.BAD_REQUEST),
    INVALID_TITLE_LENGTH("문제 내용은 500자를 넘을 수 없습니다", HttpStatus.BAD_REQUEST),
    INVALID_OPTION_SIZE("선지는 2개 이상 10개 이하로 등록해야 합니다", HttpStatus.BAD_REQUEST),
    QUIZ_NOT_FOUND("문제를 찾을 수 없습니다", HttpStatus.NOT_FOUND),

    INTERNAL_SERVER_ERROR("서버 내부 오류가 발생하였습니다", HttpStatus.INTERNAL_SERVER_ERROR),
    ;

    private final String message;
    private final HttpStatus httpStatus;
}
