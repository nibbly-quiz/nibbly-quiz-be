package com.nibbly.quiz.dto.response;

import com.nibbly.quiz.Quiz;
import com.nibbly.quiz.domain.Options;
import java.util.List;

public record QuizToSolveResponse(
        Long id,
        String title,
        List<OptionResponse> options
) {
    public static QuizToSolveResponse of(Quiz quiz, Options options) {
        List<OptionResponse> optionResponses = options.getOptionList()
                .stream()
                .map(OptionResponse::from)
                .toList();
        return new QuizToSolveResponse(quiz.getId(), quiz.getTitle(), optionResponses);
    }
}
