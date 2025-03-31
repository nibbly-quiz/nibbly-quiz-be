package com.nibbly.quiz.dto.response;

import com.nibbly.quiz.Question;
import com.nibbly.quiz.domain.Options;
import java.util.List;

public record QuizResponse(
        QuestionResponse question,
        List<OptionResponse> options
) {
    public static QuizResponse of(Question question, Options options) {
        List<OptionResponse> optionResponses = options.getOptionList()
                .stream()
                .map(OptionResponse::from)
                .toList();
        return new QuizResponse(QuestionResponse.from(question), optionResponses);
    }
}
