package com.nibbly.quiz.service;

import com.nibbly.quiz.Quiz;
import com.nibbly.quiz.domain.Options;
import com.nibbly.quiz.dto.request.QuizCreateRequest;
import com.nibbly.quiz.dto.response.QuizResponse;
import com.nibbly.quiz.dto.response.TodayQuestionsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QuizFacadeService {

    private final QuestionService questionService;
    private final OptionService optionService;

    @Transactional
    public Long saveQuiz(QuizCreateRequest request) {
        Long questionId = questionService.saveQuestion(request.getQuestion());
        optionService.saveOptions(request.getOptions(questionId));
        return questionId;
    }

    @Transactional(readOnly = true)
    public TodayQuestionsResponse getQuestionsScheduledToday() {
        return new TodayQuestionsResponse(questionService.readQuestionIdsScheduledToday());
    }

    @Transactional(readOnly = true)
    public QuizResponse getQuiz(Long quizId) {
        Quiz quiz = questionService.readQuestion(quizId);
        Options options = optionService.readOptions(quizId);
        return QuizResponse.of(quiz, options);
    }
}
