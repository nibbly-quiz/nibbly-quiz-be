package com.nibbly.quiz.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.nibbly.quiz.Question;
import java.time.LocalDate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("문제 도메인 테스트")
class QuestionTest {

    @DisplayName("문제 출제일이 특정 날짜 이전인지 확인할 수 있다.")
    @Test
    void isBefore() {
        // given
        Question question = new Question("인덱스에 대한 설명으로 올바르지 않은 것은?", LocalDate.now());

        // when & then
        Assertions.assertAll(
                () -> assertThat(question.isScheduledBefore(LocalDate.now().plusDays(1))).isTrue(),
                () -> assertThat(question.isScheduledBefore(LocalDate.now().minusDays(1))).isFalse()
        );
    }
}
