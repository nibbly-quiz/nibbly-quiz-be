package com.nibbly.quiz.dto.response;

import com.nibbly.quiz.Quiz;
import com.nibbly.quiz.domain.Options;
import java.util.List;

public record QuizResponse(
        Long id,
        String text,
        List<OptionResponse> options
) {
    public static QuizResponse of(Quiz quiz, Options options) {
        List<OptionResponse> optionResponses = options.getOptionList()
                .stream()
                .map(OptionResponse::from)
                .toList();
        return new QuizResponse(quiz.getId(), quiz.getText(), optionResponses);
    }
}
