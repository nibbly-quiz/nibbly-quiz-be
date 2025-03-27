package com.nibbly.quiz.service;

import com.nibbly.quiz.Question;
import com.nibbly.quiz.domain.QuestionRepository;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    public Long saveQuestion(Question question) {
        validateScheduleDate(question);
        return questionRepository.save(question).getId();
    }

    private void validateScheduleDate(Question question) {
        if (question.isScheduledBefore(LocalDate.now())) {
            throw new IllegalArgumentException("과거 일자에 대한 문제는 등록할 수 없습니다");
        }
    }
}
