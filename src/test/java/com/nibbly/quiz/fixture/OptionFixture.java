package com.nibbly.quiz.fixture;

import com.nibbly.quiz.domain.Option;
import com.nibbly.quiz.dto.request.OptionCreateRequest;

public enum OptionFixture {

    ANSWER_1("정답1", true),
    ANSWER_2("정답2", true),
    WRONG_1("오답1", false),
    WRONG_2("오답2", false),
    WRONG_3("오답3", false),
    WRONG_4("오답4", false),
    WRONG_5("오답5", false),
    WRONG_6("오답6", false),
    WRONG_7("오답7", false),
    WRONG_8("오답8", false),
    WRONG_9("오답9", false),
    ;

    private final String text;
    private final boolean isAnswer;

    OptionFixture(String text, boolean isAnswer) {
        this.text = text;
        this.isAnswer = isAnswer;
    }

    public Option getOption(Long quizId) {
        return new Option(quizId, text, isAnswer);
    }

    public OptionCreateRequest getOptionCreateRequest() {
        return new OptionCreateRequest(text, isAnswer);
    }
}
