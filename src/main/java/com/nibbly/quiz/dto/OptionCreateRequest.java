package com.nibbly.quiz.dto;

import com.nibbly.quiz.domain.Option;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record OptionCreateRequest(
        @NotBlank(message = "선지는 비어있을 수 없습니다.")
        @Size(max = 100, message = "선지는 최대 100자까지 입력할 수 있습니다.")
        String option,
        @NotNull(message = "정답 여부는 비어있을 수 없습니다.")
        Boolean isAnswer
) {
    public Option toEntity(Long questionId) {
        return new Option(questionId, option, isAnswer);
    }
}
