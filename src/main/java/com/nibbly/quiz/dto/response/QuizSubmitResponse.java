package com.nibbly.quiz.dto.response;

import com.nibbly.quiz.domain.Grade;

public record QuizSubmitResponse(
        Integer totalQuizCount,
        Integer correctCount,
        String rank
) {
    public static QuizSubmitResponse from(Grade grade) {
        return new QuizSubmitResponse(grade.getQuizCount(), grade.getCorrectCount(), grade.getRankDisplayName());
    }
}
