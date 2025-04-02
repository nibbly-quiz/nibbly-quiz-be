package com.nibbly.quiz.service;

import com.nibbly.quiz.Quiz;
import com.nibbly.quiz.domain.Options;
import com.nibbly.quiz.dto.request.QuizCreateRequest;
import com.nibbly.quiz.dto.response.QuizToSolveResponse;
import com.nibbly.quiz.dto.response.QuizzesResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QuizFacadeService {

    private final QuizService quizService;
    private final OptionService optionService;

    @Transactional
    public Long saveQuiz(QuizCreateRequest request) {
        Long quizId = quizService.saveQuiz(request.getQuiz());
        optionService.saveOptions(request.getOptions(quizId));
        return quizId;
    }

    @Transactional(readOnly = true)
    public QuizzesResponse findQuizzesScheduledToday() {
        return new QuizzesResponse(quizService.findQuizzesScheduledToday());
    }

    @Transactional(readOnly = true)
    public QuizToSolveResponse findQuizToSolve(Long quizId) {
        Quiz quiz = quizService.findQuiz(quizId);
        Options options = optionService.findOptions(quizId);
        return QuizToSolveResponse.of(quiz, options);
    }
}
