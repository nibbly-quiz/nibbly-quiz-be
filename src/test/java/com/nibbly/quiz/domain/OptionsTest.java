package com.nibbly.quiz.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import com.nibbly.global.exception.NibblyQuizException;
import com.nibbly.quiz.fixture.OptionFixture;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Options 테스트")
class OptionsTest {

    private static final Long QUESTION_ID = 1L;

    @DisplayName("정상적인 선지 목록은 등록할 수 있다.")
    @Test
    void should_create_options() {
        // given
        List<Option> normalOptions = OptionFixture.getOptions(
                QUESTION_ID,
                OptionFixture.ANSWER_1,
                OptionFixture.WRONG_1,
                OptionFixture.WRONG_2,
                OptionFixture.WRONG_3
        );

        // when & then
        assertThatCode(() -> new Options(normalOptions))
                .doesNotThrowAnyException();
    }

    @DisplayName("정답이 여러개인 선지 목록을 등록할 수 있다")
    @Test
    void should_create_options_when_question_has_multiple_answers() {
        // given
        List<Option> multipleAnswers = OptionFixture.getOptions(
                QUESTION_ID,
                OptionFixture.ANSWER_1,
                OptionFixture.ANSWER_2,
                OptionFixture.WRONG_1,
                OptionFixture.WRONG_2
        );

        // when & then
        assertThatCode(() -> new Options(multipleAnswers))
                .doesNotThrowAnyException();
    }

    @DisplayName("정답 뿐인 선지를 등록할 수 있다")
    @Test
    void should_create_options_when_question_has_only_answers() {
        // given
        List<Option> onlyAnswers = OptionFixture.getOptions(
                QUESTION_ID,
                OptionFixture.ANSWER_1,
                OptionFixture.ANSWER_2
        );

        // when & then
        assertThatCode(() -> new Options(onlyAnswers))
                .doesNotThrowAnyException();
    }

    @DisplayName("정답이 없는 선지 목록은 등록할 수 없다.")
    @Test
    void should_throw_exception_when_question_has_no_answer() {
        // given
        List<Option> noAnswerOptions = OptionFixture.getOptions(
                QUESTION_ID,
                OptionFixture.WRONG_1,
                OptionFixture.WRONG_2,
                OptionFixture.WRONG_3
        );

        // when & then
        assertThatThrownBy(() -> new Options(noAnswerOptions))
                .isInstanceOf(NibblyQuizException.class)
                .hasMessage("정답이 없는 문제는 등록할 수 없습니다");
    }

    @DisplayName("선지 내용이 중복되는 문제는 등록할 수 없다.")
    @Test
    void should_throw_exception_when_question_has_duplicate_options() {
        // given
        List<Option> duplicateOptions = OptionFixture.getOptions(
                QUESTION_ID,
                OptionFixture.ANSWER_1,
                OptionFixture.WRONG_1,
                OptionFixture.WRONG_1,
                OptionFixture.WRONG_2,
                OptionFixture.WRONG_3
        );

        // when & then
        assertThatThrownBy(() -> new Options(duplicateOptions))
                .isInstanceOf(NibblyQuizException.class)
                .hasMessage("선지 내용이 중복될 수 없습니다");
    }

    @DisplayName("대소문자와 공백을 무시한 선지 내용이 중복되는 문제는 등록할 수 없다")
    @Test
    void should_throw_exception_when_question_has_duplicate_options_ignoring_case_and_whitespace() {
        // given
        Option option1 = new Option(QUESTION_ID, "dupli cate", true);
        Option option2 = new Option(QUESTION_ID, "Duplicate", false);
        List<Option> duplicateOptions = List.of(option1, option2);

        // when & then
        assertThatThrownBy(() -> new Options(duplicateOptions))
                .isInstanceOf(NibblyQuizException.class)
                .hasMessage("선지 내용이 중복될 수 없습니다");
    }

    @DisplayName("선지 리스트 원본을 수정하려고 하는 경우 예외가 발생한다")
    @Test
    void should_throw_exception_when_trying_to_modify_option_list() {
        // given
        Options options = new Options(OptionFixture.getOptions(
                QUESTION_ID,
                OptionFixture.ANSWER_1,
                OptionFixture.WRONG_1,
                OptionFixture.WRONG_2,
                OptionFixture.WRONG_3
        ));

        // when & then
        assertThatThrownBy(() -> options.getOptionList().remove(0))
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @DisplayName("선지 리스트의 크기가 2 미만이면 예외가 발생한다")
    @Test
    void should_throw_exception_when_option_size_is_less_than_2() {
        // given
        List<Option> oneOption = OptionFixture.getOptions(QUESTION_ID, OptionFixture.ANSWER_1);
        ;

        // when & then
        assertThatThrownBy(() -> new Options(oneOption))
                .isInstanceOf(NibblyQuizException.class)
                .hasMessage("선지는 2개 이상 10개 이하로 등록해야 합니다");
    }

    @DisplayName("선지 리스트의 크기가 10 초과이면 예외가 발생한다")
    @Test
    void should_throw_exception_when_option_size_is_more_than_10() {
        // given
        List<Option> elevenOptions = OptionFixture.getOptions(
                QUESTION_ID,
                OptionFixture.ANSWER_1,
                OptionFixture.ANSWER_2,
                OptionFixture.WRONG_1,
                OptionFixture.WRONG_2,
                OptionFixture.WRONG_3,
                OptionFixture.WRONG_4,
                OptionFixture.WRONG_5,
                OptionFixture.WRONG_6,
                OptionFixture.WRONG_7,
                OptionFixture.WRONG_8,
                OptionFixture.WRONG_9
        );

        // when & then
        assertThatThrownBy(() -> new Options(elevenOptions))
                .isInstanceOf(NibblyQuizException.class)
                .hasMessage("선지는 2개 이상 10개 이하로 등록해야 합니다");
    }
}
