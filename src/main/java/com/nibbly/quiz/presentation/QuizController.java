package com.nibbly.quiz.presentation;

import com.nibbly.quiz.dto.request.QuizCreateRequest;
import com.nibbly.quiz.dto.response.TodayQuestionsResponse;
import com.nibbly.quiz.service.QuizFacadeService;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class QuizController {

    private final QuizFacadeService quizFacadeService;

    @PostMapping("/quizzes")
    public ResponseEntity<Void> createQuiz(@Valid @RequestBody QuizCreateRequest request) {
        Long quizId = quizFacadeService.saveQuiz(request);
        return ResponseEntity.created(URI.create("/quizzes/" + quizId)).build();
    }

    @GetMapping("/quizzes/today")
    public ResponseEntity<TodayQuestionsResponse> getTodayQuiz() {
        return ResponseEntity.ok().body(quizFacadeService.getQuestionsScheduledToday());
    }
}
