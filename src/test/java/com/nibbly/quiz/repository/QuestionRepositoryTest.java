package com.nibbly.quiz.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.nibbly.quiz.Question;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DisplayName("QuestionRepository 테스트")
@DataJpaTest
class QuestionRepositoryTest {

    @Autowired
    private QuestionRepository questionRepository;

    @DisplayName("출제 날짜로 문제를 조회할 수 있다")
    @Test
    void findByScheduledAt() {
        // given
        LocalDate today = LocalDate.now();
        Question todayScheduled1 = new Question("오늘 출제될 문제1", today);
        Question todayScheduled2 = new Question("오늘 출제될 문제2", today);

        LocalDate tomorrow = LocalDate.now().plusDays(1);
        Question tomorrowScheduled = new Question("내일 출제될 문제", tomorrow);

        questionRepository.saveAll(List.of(todayScheduled1, todayScheduled2, tomorrowScheduled));

        // when & then
        Assertions.assertAll(
                () -> assertEquals(2, questionRepository.findByScheduledAt(today).size()),
                () -> assertEquals(1, questionRepository.findByScheduledAt(tomorrow).size())
        );
    }
}
