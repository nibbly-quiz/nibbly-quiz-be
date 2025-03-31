package com.nibbly.quiz.dto.response;

import com.nibbly.quiz.Question;

public record QuestionResponse(
        Long id,
        String text
) {
    public static QuestionResponse from(Question question) {
        return new QuestionResponse(question.getId(), question.getText());
    }
}
