package com.nibbly.quiz.service;

import com.nibbly.quiz.dto.QuizCreateRequest;
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
}
