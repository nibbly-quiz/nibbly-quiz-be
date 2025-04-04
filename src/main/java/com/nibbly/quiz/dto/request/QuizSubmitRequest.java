package com.nibbly.quiz.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

public record QuizSubmitRequest(
        @NotNull(message = "퀴즈 채점 요청은 비어있을 수 없습니다")
        @Valid
        List<QuizAnswerSubmission> quizAnswerSubmissions
) {
    public int getQuizCount() {
        return quizAnswerSubmissions.size();
    }

    public record QuizAnswerSubmission(
            @NotNull(message = "퀴즈 ID는 비어있을 수 없습니다")
            Long quizId,
            Set<Long> selectedOptionIds
    ) {
    }
}
