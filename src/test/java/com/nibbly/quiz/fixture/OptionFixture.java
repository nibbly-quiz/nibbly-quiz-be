package com.nibbly.quiz.fixture;

import com.nibbly.quiz.domain.Option;
import com.nibbly.quiz.domain.Options;
import com.nibbly.quiz.dto.request.OptionCreateRequest;
import java.util.Arrays;
import java.util.List;

public enum OptionFixture {

    ANSWER_1("정답1", null, true),
    ANSWER_2("정답2", null, true),
    WRONG_1("오답1", OptionFixture.DEFAULT_CORRECTION, false),
    WRONG_2("오답2", OptionFixture.DEFAULT_CORRECTION, false),
    WRONG_3("오답3", OptionFixture.DEFAULT_CORRECTION, false),
    WRONG_4("오답4", OptionFixture.DEFAULT_CORRECTION, false),
    WRONG_5("오답5", OptionFixture.DEFAULT_CORRECTION, false),
    WRONG_6("오답6", OptionFixture.DEFAULT_CORRECTION, false),
    WRONG_7("오답7", OptionFixture.DEFAULT_CORRECTION, false),
    WRONG_8("오답8", OptionFixture.DEFAULT_CORRECTION, false),
    WRONG_9("오답9", OptionFixture.DEFAULT_CORRECTION, false),
    ;

    private static final String DEFAULT_CORRECTION = "특정 이유 때문에 해당 선지는 오답입니다";

    private final String content;
    private final String correction;
    private final boolean isAnswer;

    OptionFixture(String content, String correction, boolean isAnswer) {
        this.content = content;
        this.correction = correction;
        this.isAnswer = isAnswer;
    }

    public static List<Option> getOptionList(Long quizId, OptionFixture... optionFixtures) {
        return Arrays.stream(optionFixtures)
                .map(optionFixture -> optionFixture.getOption(quizId))
                .toList();
    }

    public static Options getOptions(Long quizId, OptionFixture... optionFixtures) {
        return new Options(getOptionList(quizId, optionFixtures));
    }

    public Option getOption(Long quizId) {
        return new Option(quizId, content, correction, isAnswer);
    }

    public OptionCreateRequest getOptionCreateRequest() {
        return new OptionCreateRequest(content, correction, isAnswer);
    }

    public String getContent() {
        return content;
    }

    public String getCorrection() {
        return correction;
    }

    public boolean isAnswer() {
        return isAnswer;
    }
}
