package com.nibbly.quiz.dto.response;

import com.nibbly.quiz.Quiz;
import com.nibbly.quiz.domain.Option;
import com.nibbly.quiz.domain.Options;
import java.util.List;

public record QuizCreateResponse(
        Long quizId,
        List<Long> optionIds
) {
    public static QuizCreateResponse of(Quiz quiz, Options options) {
        List<Long> optionIds = options.getOptionList().stream()
                .map(Option::getId)
                .toList();
        return new QuizCreateResponse(quiz.getId(), optionIds);
    }
}
