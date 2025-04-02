package com.nibbly.quiz.fixture;

import java.time.LocalDate;

public enum QuizFixture {

    QUIZ("퀴즈", LocalDate.now());

    private final String text;
    private final LocalDate scheduledAt;

    QuizFixture(String text, LocalDate scheduledAt) {
        this.text = text;
        this.scheduledAt = scheduledAt;
    }

}
