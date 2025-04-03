package com.nibbly.quiz.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import com.nibbly.global.exception.NibblyQuizException;
import com.nibbly.quiz.Quiz;
import java.time.LocalDate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("문제 도메인 테스트")
class QuizTest {

    private static final LocalDate TOMORROW = LocalDate.now().plusDays(1);
    private static final LocalDate YESTERDAY = LocalDate.now().minusDays(1);

    @DisplayName("문제 생성이 가능하다")
    @Test
    void should_create_quiz() {
        // given
        String title = "문제 내용";
        String commentary = "문제 해설";
        LocalDate scheduleAt = LocalDate.now();

        // when & then
        assertThatCode(() -> new Quiz(title, scheduleAt, commentary))
                .doesNotThrowAnyException();
    }

    @DisplayName("문제 생성 시 문제 내용이 500자를 넘어가면 예외가 발생한다")
    @Test
    void should_throw_exception_when_text_length_exceeds_500() {
        // given
        String title = "a".repeat(501);
        String commentary = "문제 해설";
        LocalDate scheduleAt = LocalDate.now();

        // when & then
        assertThatCode(() -> new Quiz(title, scheduleAt, commentary))
                .isInstanceOf(NibblyQuizException.class)
                .hasMessage("문제 내용은 500자를 넘을 수 없습니다");
    }

    @DisplayName("문제 출제일이 특정 날짜 이전인지 확인할 수 있다.")
    @Test
    void should_return_true_when_quiz_is_scheduled_before_target() {
        // given
        String title = "문제 내용";
        String commentary = "문제 해설";
        LocalDate scheduleAt = LocalDate.now();
        Quiz quiz = new Quiz(title, LocalDate.now(), commentary);

        // when & then
        Assertions.assertAll(
                () -> assertThat(quiz.isScheduledBefore(TOMORROW)).isTrue(),
                () -> assertThat(quiz.isScheduledBefore(YESTERDAY)).isFalse()
        );
    }

    @DisplayName("퀴즈 해설이 5000자를 넘어가면 예외가 발생한다")
    @Test
    void should_throw_exception_when_commentary_length_exceeds_5000() {
        // given
        String title = "문제 내용";
        String commentary = "a".repeat(5001);
        LocalDate scheduleAt = LocalDate.now();

        // when & then
        assertThatCode(() -> new Quiz(title, scheduleAt, commentary))
                .isInstanceOf(NibblyQuizException.class)
                .hasMessage("퀴즈 해설은 5000자를 넘을 수 없습니다");
    }
}
