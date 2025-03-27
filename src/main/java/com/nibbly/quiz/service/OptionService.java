package com.nibbly.quiz.service;

import com.nibbly.quiz.domain.Option;
import com.nibbly.quiz.domain.OptionRepository;
import com.nibbly.quiz.global.exception.ErrorCode;
import com.nibbly.quiz.global.exception.NibblyQuizException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OptionService {

    private final OptionRepository optionRepository;

    public void saveOptions(List<Option> options) {
        validateHasAnswer(options);
        validateNoDuplication(options);
        optionRepository.saveAll(options);
    }

    private void validateHasAnswer(List<Option> options) {
        boolean noAnswer = options.stream()
                .noneMatch(Option::isAnswer);
        if (noAnswer) {
            throw new NibblyQuizException(ErrorCode.NO_CORRECT_ANSWER);
        }
    }

    private void validateNoDuplication(List<Option> options) {
        boolean hasDuplication = options.stream()
                .map(Option::getText)
                .distinct()
                .count() != options.size();
        if (hasDuplication) {
            throw new NibblyQuizException(ErrorCode.DUPLICATE_OPTIONS);
        }
    }
}
