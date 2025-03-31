package com.nibbly.quiz.service;

import com.nibbly.quiz.Question;
import com.nibbly.quiz.domain.QuestionRepository;
import com.nibbly.quiz.global.exception.ErrorCode;
import com.nibbly.quiz.global.exception.NibblyQuizException;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    @Transactional
    public Long saveQuestion(Question question) {
        validateScheduleDate(question);
        return questionRepository.save(question).getId();
    }

    private void validateScheduleDate(Question question) {
        if (question.isScheduledBefore(LocalDate.now())) {
            throw new NibblyQuizException(ErrorCode.INVALID_SCHEDULE_DATE);
        }
    }
}
