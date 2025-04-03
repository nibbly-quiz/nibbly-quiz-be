package com.nibbly.quiz.dto.response;

import com.nibbly.quiz.Quiz;
import java.util.List;

public record QuizzesResponse(
        List<Long> quizIds
) {
    public static QuizzesResponse from(List<Quiz> quizzesScheduledToday) {
        return new QuizzesResponse(
                quizzesScheduledToday.stream()
                        .map(Quiz::getId)
                        .toList()
        );
    }
}
