package com.nibbly.quiz.presentation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class QuizViewController {

    @GetMapping("/quiz/new")
    public String newQuiz() {
        return "quiz-form";
    }

    @GetMapping("/quiz/today")
    public String solveQuiz() {
        return "quiz-today";
    }

    @GetMapping("/quiz/grade")
    public String gradeQuiz() {
        return "quiz-grade";
    }
}
