package com.nibbly.quiz.dto.response;

import java.util.List;

public record QuizzesResponse(
        List<Long> quizIds
) {
}
