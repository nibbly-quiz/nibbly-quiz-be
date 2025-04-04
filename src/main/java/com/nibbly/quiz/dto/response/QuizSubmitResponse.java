package com.nibbly.quiz.dto.response;

public record QuizSubmitResponse(
        Integer totalQuizCount,
        Integer correctCount
) {
}
