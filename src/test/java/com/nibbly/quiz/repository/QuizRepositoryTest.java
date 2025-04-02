package com.nibbly.quiz.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.nibbly.quiz.Quiz;
import com.nibbly.quiz.fixture.QuizFixture;
import java.time.LocalDate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DisplayName("QuestionRepository 테스트")
@DataJpaTest
class QuizRepositoryTest {

    @Autowired
    private QuestionRepository questionRepository;

    @DisplayName("출제 날짜로 문제를 조회할 수 있다")
    @Test
    void findByScheduledAt() {
        // given
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = LocalDate.now().plusDays(1);

        Quiz quizScheduledToday = QuizFixture.QUIZ.getQuestionScheduledAt(today);
        questionRepository.save(quizScheduledToday);

        // when & then
        Assertions.assertAll(
                () -> assertEquals(1, questionRepository.findByScheduledAt(today).size()),
                () -> assertEquals(0, questionRepository.findByScheduledAt(tomorrow).size())
        );
    }
}
