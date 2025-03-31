package com.nibbly.quiz.dto;

import com.nibbly.quiz.Question;
import java.util.List;

public record TodayQuestionsResponse(
        List<QuestionResponse> questionResponses
) {
    public static TodayQuestionsResponse from(List<Question> todayQuestions) {
        return new TodayQuestionsResponse(todayQuestions.stream()
                .map(QuestionResponse::from)
                .toList());
    }
}
