package com.nibbly.quiz.service;

import com.nibbly.global.exception.ErrorCode;
import com.nibbly.global.exception.NibblyQuizException;
import com.nibbly.quiz.Quiz;
import com.nibbly.quiz.repository.QuestionRepository;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    @Transactional
    public Long saveQuestion(Quiz quiz) {
        validateScheduleDate(quiz);
        return questionRepository.save(quiz).getId();
    }

    private void validateScheduleDate(Quiz quiz) {
        if (quiz.isScheduledBefore(LocalDate.now())) {
            throw new NibblyQuizException(ErrorCode.INVALID_SCHEDULE_DATE);
        }
    }

    @Transactional(readOnly = true)
    public List<Long> readQuestionIdsScheduledToday() {
        return questionRepository.findByScheduledAt(LocalDate.now());
    }

    @Transactional(readOnly = true)
    public Quiz readQuestion(Long questionId) {
        return questionRepository.findById(questionId)
                .orElseThrow(() -> new NibblyQuizException(ErrorCode.QUESTION_NOT_FOUND));
    }
}
