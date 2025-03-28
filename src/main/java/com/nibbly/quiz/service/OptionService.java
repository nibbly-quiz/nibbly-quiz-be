package com.nibbly.quiz.service;

import com.nibbly.quiz.domain.Option;
import com.nibbly.quiz.domain.OptionRepository;
import com.nibbly.quiz.global.exception.ErrorCode;
import com.nibbly.quiz.global.exception.NibblyQuizException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OptionService {

    private final OptionRepository optionRepository;

    @Transactional
    public void saveOptions(List<Option> options) {
        validateHasAnswer(options);
        validateNoDuplication(options);
        optionRepository.saveAll(options);
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
}
