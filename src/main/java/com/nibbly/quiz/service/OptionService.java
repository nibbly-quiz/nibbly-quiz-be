package com.nibbly.quiz.service;

import com.nibbly.quiz.domain.Options;
import com.nibbly.quiz.repository.OptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OptionService {

    private final OptionRepository optionRepository;

    @Transactional
    public void saveOptions(Options options) {
        optionRepository.saveAll(options.getOptionList());
    }

    @Transactional(readOnly = true)
    public Options readOptions(Long quizId) {
        return new Options(optionRepository.findByQuizId(quizId));
    }
}
