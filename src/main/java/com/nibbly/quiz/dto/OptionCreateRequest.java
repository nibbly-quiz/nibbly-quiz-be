package com.nibbly.quiz.dto;

import com.nibbly.quiz.domain.Option;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record OptionCreateRequest(
        @NotBlank(message = "선지는 비어있을 수 없습니다.")
        String option,
        @NotNull(message = "정답 여부는 비어있을 수 없습니다.")
        Boolean isAnswer
) {
    public Option toEntity(Long questionId) {
        return new Option(questionId, option, isAnswer);
    }
}
