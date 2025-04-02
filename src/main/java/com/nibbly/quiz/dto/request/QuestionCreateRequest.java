package com.nibbly.quiz.dto.request;

import com.nibbly.quiz.Quiz;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record QuestionCreateRequest(
        @NotBlank(message = "질문 내용은 비어있을 수 없습니다.")
        String text,
        @NotNull(message = "출제 날짜는 비어있을 수 없습니다.")
        LocalDate scheduleAt
) {
    public Quiz toEntity() {
        return new Quiz(text, scheduleAt);
    }
}
