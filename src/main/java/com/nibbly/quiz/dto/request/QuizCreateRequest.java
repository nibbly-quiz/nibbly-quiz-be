package com.nibbly.quiz.dto.request;

import com.nibbly.quiz.Question;
import com.nibbly.quiz.domain.Options;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record QuizCreateRequest(
        @NotNull(message = "질문은 비어있을 수 없습니다")
        @Valid
        QuestionCreateRequest questionRequest,
        @NotNull(message = "선지는 비어있을 수 없습니다")
        @Valid
        List<OptionCreateRequest> optionCreateRequests
) {
    public Question getQuestion() {
        return questionRequest.toEntity();
    }

    public Options getOptions(Long questionId) {
        return new Options(optionCreateRequests.stream()
                .map(optionCreateRequest -> optionCreateRequest.toEntity(questionId))
                .toList());
    }
}
