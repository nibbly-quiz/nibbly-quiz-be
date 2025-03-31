package com.nibbly.quiz.domain;

import com.nibbly.quiz.global.exception.ErrorCode;
import com.nibbly.quiz.global.exception.NibblyQuizException;
import java.util.Collections;
import java.util.List;

public class Options {

    private static final int MIN_SIZE = 2;
    private static final int MAX_SIZE = 10;

    private final List<Option> options;

    public Options(List<Option> options) {
        validateHasAnswer(options);
        validateNoDuplication(options);
        validateSize(options);
        this.options = options;
    }

    private void validateHasAnswer(List<Option> options) {
        if (hasNoAnswer(options)) {
            throw new NibblyQuizException(ErrorCode.NO_CORRECT_ANSWER);
        }
    }

    private boolean hasNoAnswer(List<Option> options) {
        return options.stream()
                .noneMatch(Option::isAnswer);
    }

    private void validateNoDuplication(List<Option> options) {
        if (isDuplicated(options)) {
            throw new NibblyQuizException(ErrorCode.DUPLICATE_OPTIONS);
        }
    }

    private boolean isDuplicated(List<Option> options) {
        return options.stream()
                .map(Option::getText)
                .map(text -> text.replaceAll("\\s", "").toLowerCase())
                .distinct()
                .count() != options.size();
    }

    private void validateSize(List<Option> options) {
        if (options.size() < MIN_SIZE || options.size() > MAX_SIZE) {
            throw new NibblyQuizException(ErrorCode.INVALID_OPTION_SIZE);
        }
    }

    public List<Option> getOptionList() {
        return Collections.unmodifiableList(options);
    }
}
