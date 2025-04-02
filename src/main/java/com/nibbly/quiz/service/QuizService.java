package com.nibbly.quiz.service;

import com.nibbly.global.exception.ErrorCode;
import com.nibbly.global.exception.NibblyQuizException;
import com.nibbly.quiz.Quiz;
import com.nibbly.quiz.repository.QuizRepository;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final QuizRepository quizRepository;

    @Transactional
    public Long saveQuiz(Quiz quiz) {
        validateScheduledDate(quiz);
        return quizRepository.save(quiz).getId();
    }

    private void validateScheduledDate(Quiz quiz) {
        if (quiz.isScheduledBefore(LocalDate.now())) {
            throw new NibblyQuizException(ErrorCode.INVALID_SCHEDULE_DATE);
        }
    }

    @Transactional(readOnly = true)
    public List<Long> findQuizzesScheduledToday() {
        return quizRepository.findByScheduledAt(LocalDate.now());
    }

    @Transactional(readOnly = true)
    public Quiz findQuiz(Long quizId) {
        return quizRepository.findById(quizId)
                .orElseThrow(() -> new NibblyQuizException(ErrorCode.QUIZ_NOT_FOUND));
    }
}
