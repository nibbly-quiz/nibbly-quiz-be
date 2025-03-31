package com.nibbly.quiz.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import com.nibbly.global.exception.NibblyQuizException;
import com.nibbly.quiz.Question;
import java.time.LocalDate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("문제 도메인 테스트")
class QuestionTest {

    @DisplayName("문제 생성이 가능하다")
    @Test
    void should_create_question() {
        // given
        String text = "문제 내용";
        LocalDate scheduleAt = LocalDate.now();

        // when & then
        assertThatCode(() -> new Question(text, scheduleAt))
                .doesNotThrowAnyException();
    }

    @DisplayName("문제 생성 시 문제 내용이 500자를 넘어가면 예외가 발생한다")
    @Test
    void should_throw_exception_when_text_length_exceeds_500() {
        // given
        String text = "a".repeat(501);
        LocalDate scheduleAt = LocalDate.now();

        // when & then
        assertThatCode(() -> new Question(text, scheduleAt))
                .isInstanceOf(NibblyQuizException.class)
                .hasMessage("문제 내용은 500자를 넘을 수 없습니다");
    }

    @DisplayName("문제 출제일이 특정 날짜 이전인지 확인할 수 있다.")
    @Test
    void should_return_true_when_question_is_scheduled_before_target() {
        // given
        Question question = new Question("인덱스에 대한 설명으로 올바르지 않은 것은?", LocalDate.now());

        // when & then
        Assertions.assertAll(
                () -> assertThat(question.isScheduledBefore(LocalDate.now().plusDays(1))).isTrue(),
                () -> assertThat(question.isScheduledBefore(LocalDate.now().minusDays(1))).isFalse()
        );
    }
}
