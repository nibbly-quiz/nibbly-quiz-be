package com.nibbly.quiz.dto.response;

import com.nibbly.quiz.Quiz;
import java.util.List;

public record QuizzesToSolveResponse(
        List<Long> quizIds
) {
    public static QuizzesToSolveResponse from(List<Quiz> quizzesScheduledToday) {
        return new QuizzesToSolveResponse(
                quizzesScheduledToday.stream()
                        .map(Quiz::getId)
                        .toList()
        );
    }
}
