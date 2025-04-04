package com.nibbly.quiz.service;

import com.nibbly.quiz.domain.Options;
import com.nibbly.quiz.repository.OptionRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OptionService {

    private final OptionRepository optionRepository;

    @Transactional
    public Options saveOptions(Options options) {
        return new Options(optionRepository.saveAll(options.getOptionList()));
    }

    @Transactional(readOnly = true)
    public Options findOptions(Long quizId) {
        return new Options(optionRepository.findByQuizId(quizId));
    }

    @Transactional(readOnly = true)
    public boolean isCorrectAnswer(Long quizId, List<Long> selectedOptionIds) {
        Options options = new Options(optionRepository.findByQuizId(quizId));
        return options.isCorrectAnswer(selectedOptionIds);
    }
}
