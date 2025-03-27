package com.nibbly.quiz.service;

import com.nibbly.quiz.domain.Option;
import com.nibbly.quiz.domain.OptionRepository;
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
            throw new IllegalArgumentException("정답이 없는 문제는 등록할 수 없습니다");
        }
    }

    private void validateNoDuplication(List<Option> options) {
        boolean hasDuplication = options.stream()
                .map(Option::getText)
                .distinct()
                .count() != options.size();
        if (hasDuplication) {
            throw new IllegalArgumentException("선지 내용이 중복되는 문제는 등록할 수 없습니다");
        }
    }
}
