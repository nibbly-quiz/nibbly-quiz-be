package com.nibbly.quiz.fixture;

import com.nibbly.quiz.Quiz;
import com.nibbly.quiz.dto.request.QuizCreateRequest;
import java.time.LocalDate;
import java.util.List;

public enum QuizFixture {

    QUIZ(
            "퀴즈",
            "퀴즈 해설",
            LocalDate.now(),
            List.of(OptionFixture.ANSWER_1, OptionFixture.WRONG_1, OptionFixture.WRONG_2, OptionFixture.WRONG_3)
    ),
    ;

    private final String title;
    private final String commentary;
    private final LocalDate scheduledAt;
    private final List<OptionFixture> options;

    QuizFixture(String title, String commentary, LocalDate scheduledAt, List<OptionFixture> options) {
        this.title = title;
        this.commentary = commentary;
        this.scheduledAt = scheduledAt;
        this.options = options;
    }

    public Quiz getQuiz() {
        return new Quiz(title, scheduledAt, commentary);
    }

    public Quiz getQuizScheduledAt(LocalDate scheduledAt) {
        return new Quiz(title, scheduledAt, commentary);
    }

    public QuizCreateRequest getQuizCreateRequest() {
        return new QuizCreateRequest(
                title,
                scheduledAt,
                commentary,
                options.stream()
                        .map(OptionFixture::getOptionCreateRequest)
                        .toList()
        );
    }
}
