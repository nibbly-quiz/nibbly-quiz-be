package com.nibbly.quiz.dto.response;

import java.util.List;

public record TodayQuestionsResponse(
        List<Long> quizIds
) {
}
