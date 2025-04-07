package com.nibbly.quiz.service;

import com.nibbly.quiz.Quiz;
import com.nibbly.quiz.domain.Options;
import com.nibbly.quiz.dto.request.QuizCreateRequest;
import com.nibbly.quiz.dto.request.QuizzesSubmitRequest;
import com.nibbly.quiz.dto.response.QuizCreateResponse;
import com.nibbly.quiz.dto.response.QuizSubmitResponse;
import com.nibbly.quiz.dto.response.QuizToSolveResponse;
import com.nibbly.quiz.dto.response.QuizzesToSolveResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QuizFacadeService {

    private final QuizService quizService;
    private final OptionService optionService;
    private final GradeService gradeService;

    @Transactional
    public QuizCreateResponse saveQuiz(QuizCreateRequest request) {
        Quiz quiz = quizService.saveQuiz(request.getQuiz());
        Options options = optionService.saveOptions(request.getOptions(quiz.getId()));
        return QuizCreateResponse.of(quiz, options);
    }

    @Transactional(readOnly = true)
    public QuizzesToSolveResponse findQuizzesScheduledToday() {
        return QuizzesToSolveResponse.from(quizService.findQuizzesScheduledToday());
    }

    @Transactional(readOnly = true)
    public QuizToSolveResponse findQuizToSolve(Long quizId) {
        Quiz quiz = quizService.findQuiz(quizId);
        Options options = optionService.findOptions(quizId);
        return QuizToSolveResponse.of(quiz, options);
    }

    @Transactional(readOnly = true)
    public QuizSubmitResponse submitQuiz(QuizzesSubmitRequest request) {
        return QuizSubmitResponse.from(gradeService.gradeUserAnswers(request.toUserAnswers()));
    }
}
