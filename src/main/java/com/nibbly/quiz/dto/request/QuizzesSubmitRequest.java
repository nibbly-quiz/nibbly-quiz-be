package com.nibbly.quiz.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public record QuizzesSubmitRequest(
        @NotNull(message = "퀴즈 채점 요청은 비어있을 수 없습니다")
        @Valid
        List<QuizSubmitRequest> quizSubmitRequests
) {

    public Map<Long, Set<Long>> toUserAnswers() {
        return quizSubmitRequests.stream()
                .collect(Collectors.toMap(QuizSubmitRequest::quizId, QuizSubmitRequest::selectedOptionIds));
    }

    public record QuizSubmitRequest(
            @NotNull(message = "퀴즈 ID는 비어있을 수 없습니다")
            Long quizId,
            Set<Long> selectedOptionIds
    ) {
    }
}
