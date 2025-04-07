package com.nibbly.quiz.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.nibbly.global.exception.NibblyQuizException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Option 테스트")
class OptionTest {

    private static final Long QUIZ_ID = 1L;

    @DisplayName("정상적인 선지는 등록 가능하다")
    @Test
    void should_create_option() {
        // given
        String content = "정상적인 선지";

        // when & then
        assertThatCode(() -> new Option(QUIZ_ID, content, null, true))
                .doesNotThrowAnyException();
    }

    @DisplayName("정답 선지와 오답 선지에 해설을 첨부할 수 있다")
    @Test
    void should_create_option_with_correction() {
        // given
        String content = "선지";
        String correction = "해설";

        // when & then
        Assertions.assertAll(
                () -> assertThatCode(() -> new Option(QUIZ_ID, content, correction, true))
                        .doesNotThrowAnyException(),
                () -> assertThatCode(() -> new Option(QUIZ_ID, content, correction, false))
                        .doesNotThrowAnyException()
        );
    }

    @DisplayName("선지 길이가 100자 초과인 경우 예외가 발생한다")
    @Test
    void should_throw_exception_when_text_length_exceeds_100() {
        // given
        String content = "a".repeat(101);

        // when & then
        assertThatThrownBy(() -> new Option(QUIZ_ID, content, null, true))
                .isInstanceOf(NibblyQuizException.class)
                .hasMessage("선지 내용은 100자를 넘을 수 없습니다");
    }

    @DisplayName("오답 선지에 해설이 존재하지 않는 경우 예외가 발생한다")
    @Test
    void should_throw_exception_when_answer_is_false_and_correction_is_null() {
        // given
        String content = "오답 선지";

        // when & then
        assertThatThrownBy(() -> new Option(QUIZ_ID, content, null, false))
                .isInstanceOf(NibblyQuizException.class)
                .hasMessage("오답 선지에는 해설을 필수로 등록해야 합니다");
    }

    @DisplayName("해설 길이가 100자 초과인 경우 예외가 발생한다")
    @Test
    void should_throw_exception_when_correction_length_exceeds_100() {
        // given
        String content = "정상적인 선지";
        String correction = "a".repeat(101);

        // when & then
        assertThatThrownBy(() -> new Option(QUIZ_ID, content, correction, true))
                .isInstanceOf(NibblyQuizException.class)
                .hasMessage("선지 해설은 100자를 넘을 수 없습니다");
    }
}
