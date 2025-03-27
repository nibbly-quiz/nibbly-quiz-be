package com.nibbly.quiz.service;

import com.nibbly.quiz.Question;
import com.nibbly.quiz.domain.Option;
import com.nibbly.quiz.domain.OptionRepository;
import com.nibbly.quiz.domain.QuestionRepository;
import com.nibbly.quiz.dto.QuizCreateRequest;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final QuestionRepository questionRepository;
    private final OptionRepository optionRepository;

    @Transactional
    public void saveQuiz(QuizCreateRequest request) {
        Long questionId = saveQuestion(request.getQuestion());
        saveOptions(request.getOptions(questionId));
    }

    private Long saveQuestion(Question question) {
        validateScheduleDate(question);
        return questionRepository.save(question).getId();
    }

    private void validateScheduleDate(Question question) {
        if (question.isScheduledBefore(LocalDate.now())) {
            throw new IllegalArgumentException("과거 일자에 대한 문제는 등록할 수 없습니다");
        }
    }

    private void saveOptions(List<Option> options) {
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
