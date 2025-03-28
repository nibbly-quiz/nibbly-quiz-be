package com.nibbly.quiz.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import com.nibbly.quiz.global.exception.NibblyQuizException;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Options 테스트")
class OptionsTest {

    @DisplayName("정상적인 선지 목록은 등록할 수 있다.")
    @Test
    void should_create_options() {
        // given
        Long questionId = 1L;
        Option option1 = new Option(questionId, "오답1", false);
        Option option2 = new Option(questionId, "오답2", false);
        Option option3 = new Option(questionId, "오답3", false);
        Option option4 = new Option(questionId, "정답", true);

        // when & then
        assertThatCode(() -> new Options(List.of(option1, option2, option3, option4)))
                .doesNotThrowAnyException();
    }

    @DisplayName("정답이 없는 선지 목록은 등록할 수 없다.")
    @Test
    void should_throw_exception_when_question_has_no_answer() {
        // given
        Long questionId = 1L;
        Option option1 = new Option(questionId, "오답1", false);
        Option option2 = new Option(questionId, "오답2", false);
        Option option3 = new Option(questionId, "오답3", false);
        Option option4 = new Option(questionId, "오답4", false);

        // when & then
        assertThatThrownBy(() -> new Options(List.of(option1, option2, option3, option4)))
                .isInstanceOf(NibblyQuizException.class)
                .hasMessage("정답이 없는 문제는 등록할 수 없습니다");
    }

    @DisplayName("선지 내용이 중복되는 문제는 등록할 수 없다. - 공백, 대소문자 무시")
    @Test
    void should_throw_exception_when_question_has_duplicate_options() {
        // given
        Long questionId = 1L;
        Option option1 = new Option(questionId, "duplicate", false);
        Option option2 = new Option(questionId, "Dup liCa te", true);

        // when & then
        assertThatThrownBy(() -> new Options(List.of(option1, option2)))
                .isInstanceOf(NibblyQuizException.class)
                .hasMessage("선지 내용이 중복될 수 없습니다");
    }

    @DisplayName("선지 리스트 원본을 수정하려고 하는 경우 예외가 발생한다")
    @Test
    void should_throw_exception_when_trying_to_modify_option_list() {
        // given
        Long questionId = 1L;
        Option option1 = new Option(questionId, "오답1", false);
        Option option2 = new Option(questionId, "오답2", false);
        Option option3 = new Option(questionId, "오답3", false);
        Option option4 = new Option(questionId, "정답", true);
        Options options = new Options(List.of(option1, option2, option3, option4));
        List<Option> optionList = options.getOptionList();

        // when & then
        assertThatThrownBy(() -> optionList.add(new Option(questionId, "오답4", false)))
                .isInstanceOf(UnsupportedOperationException.class);
    }
}
