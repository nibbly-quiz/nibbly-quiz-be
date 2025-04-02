package com.nibbly.quiz.dto.response;

import com.nibbly.quiz.Quiz;

public record QuestionResponse(
        Long id,
        String text
) {
    public static QuestionResponse from(Quiz quiz) {
        return new QuestionResponse(quiz.getId(), quiz.getText());
    }
}
