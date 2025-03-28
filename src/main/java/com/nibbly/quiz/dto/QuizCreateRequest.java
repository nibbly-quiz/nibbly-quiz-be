package com.nibbly.quiz.dto;

import com.nibbly.quiz.Question;
import com.nibbly.quiz.domain.Option;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

public record QuizCreateRequest(
        @NotNull(message = "질문은 비어있을 수 없습니다")
        @Valid
        QuestionCreateRequest questionRequest,
        @NotNull(message = "선지는 비어있을 수 없습니다")
        @Size(message = "문제당 선지는 최소 2개 이상, 10개 이하여야 합니다", min = 2, max = 10)
        @Valid
        List<OptionCreateRequest> optionCreateRequests
) {
    public Question getQuestion() {
        return questionRequest.toEntity();
    }

    public List<Option> getOptions(Long questionId) {
        return optionCreateRequests.stream()
                .map(optionCreateRequest -> optionCreateRequest.toEntity(questionId))
                .toList();
    }
}
