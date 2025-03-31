package com.nibbly.quiz.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.nibbly.quiz.global.exception.NibblyQuizException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Option 테스트")
class OptionTest {

    @DisplayName("정상적인 선지는 등록 가능하다")
    @Test
    void should_create_option() {
        // given
        String text = "정상적인 선지";
        Long questionId = 1L;

        // when & then
        assertThatCode(() -> new Option(1L, text, true))
                .doesNotThrowAnyException();
    }

    @DisplayName("선지 길이가 100자 초과인 경우 예외가 발생한다")
    @Test
    void should_throw_exception_when_text_length_exceeds_100() {
        // given
        String text = "a".repeat(101);
        Long questionId = 1L;

        // when & then
        assertThatThrownBy(() -> new Option(1L, text, false))
                .isInstanceOf(NibblyQuizException.class)
                .hasMessage("선지 내용은 100자를 넘을 수 없습니다");
    }
}
