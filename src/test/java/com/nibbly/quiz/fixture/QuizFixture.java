package com.nibbly.quiz.fixture;

import com.nibbly.quiz.Quiz;
import com.nibbly.quiz.dto.request.QuizCreateRequest;
import java.time.LocalDate;
import java.util.List;

public enum QuizFixture {

    QUIZ(
            "퀴즈",
            LocalDate.now(),
            List.of(OptionFixture.ANSWER_1, OptionFixture.WRONG_1, OptionFixture.WRONG_2, OptionFixture.WRONG_3)
    ),
    ;

    private final String title;
    private final LocalDate scheduledAt;
    private final List<OptionFixture> options;

    QuizFixture(String title, LocalDate scheduledAt, List<OptionFixture> options) {
        this.title = title;
        this.scheduledAt = scheduledAt;
        this.options = options;
    }

    public Quiz getQuestion() {
        return new Quiz(title, scheduledAt);
    }

    public Quiz getQuestionScheduledAt(LocalDate scheduledAt) {
        return new Quiz(title, scheduledAt);
    }

    public QuizCreateRequest getQuizCreateRequest() {
        return new QuizCreateRequest(
                title,
                scheduledAt,
                options.stream()
                        .map(OptionFixture::getOptionCreateRequest)
                        .toList()
        );
    }
}
