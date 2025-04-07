package com.nibbly.quiz.service;

import com.nibbly.quiz.domain.AnswerSheet;
import com.nibbly.quiz.domain.Grade;
import com.nibbly.quiz.repository.OptionRepository;
import java.util.Map;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GradeService {

    private final OptionRepository optionRepository;

    @Transactional(readOnly = true)
    public Grade gradeUserAnswers(Map<Long, Set<Long>> userAnswers) {
        AnswerSheet answerSheet = findAnswerSheet(userAnswers.keySet());
        return new Grade(userAnswers.size(), answerSheet.countCorrect(userAnswers));
    }

    private AnswerSheet findAnswerSheet(Set<Long> quizIds) {
        return AnswerSheet.from(optionRepository.findAnswerOptions(quizIds));
    }
}
