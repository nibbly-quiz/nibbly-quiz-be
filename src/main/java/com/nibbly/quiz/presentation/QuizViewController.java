package com.nibbly.quiz.presentation;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class QuizViewController {

    @GetMapping("/quiz/new")
    public String newQuiz(Model model) {
        return "quiz-form";
    }
}
