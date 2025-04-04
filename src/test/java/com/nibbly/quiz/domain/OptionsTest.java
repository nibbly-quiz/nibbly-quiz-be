package com.nibbly.quiz.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import com.nibbly.global.exception.NibblyQuizException;
import com.nibbly.quiz.fixture.OptionFixture;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Options 테스트")
class OptionsTest {

    private static final Long QUIZ_ID = 1L;

    @DisplayName("정상적인 선지 목록은 등록할 수 있다.")
    @Test
    void should_create_options() {
        // given
        List<Option> normalOptions = OptionFixture.getOptionList(
                QUIZ_ID,
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
    void should_create_options_when_quiz_has_multiple_answers() {
        // given
        List<Option> multipleAnswers = OptionFixture.getOptionList(
                QUIZ_ID,
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
    void should_create_options_when_quiz_has_only_answers() {
        // given
        List<Option> onlyAnswers = OptionFixture.getOptionList(
                QUIZ_ID,
                OptionFixture.ANSWER_1,
                OptionFixture.ANSWER_2
        );

        // when & then
        assertThatCode(() -> new Options(onlyAnswers))
                .doesNotThrowAnyException();
    }

    @DisplayName("정답이 없는 선지 목록은 등록할 수 없다.")
    @Test
    void should_throw_exception_when_quiz_has_no_answer() {
        // given
        List<Option> noAnswerOptions = OptionFixture.getOptionList(
                QUIZ_ID,
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
    void should_throw_exception_when_quiz_has_duplicate_options() {
        // given
        List<Option> duplicateOptions = OptionFixture.getOptionList(
                QUIZ_ID,
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
    void should_throw_exception_when_quiz_has_duplicate_options_ignoring_case_and_whitespace() {
        // given
        Option option1 = new Option(QUIZ_ID, "dupli cate", null, true);
        Option option2 = new Option(QUIZ_ID, "Duplicate", "오답인 이유가 있습니다", false);
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
        Options options = new Options(OptionFixture.getOptionList(
                QUIZ_ID,
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
        List<Option> oneOption = OptionFixture.getOptionList(QUIZ_ID, OptionFixture.ANSWER_1);
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
        List<Option> elevenOptions = OptionFixture.getOptionList(
                QUIZ_ID,
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

    @DisplayName("옵션 ID가 정답인 경우 true를 반환한다")
    @Test
    void should_return_true_when_option_id_is_answer() {
        // given
        List<Option> optionRaws = List.of(
                new Option(1L, 1L, "정답", "정답입니다", true),
                new Option(2L, 1L, "오답1", "오답입니다", false),
                new Option(3L, 1L, "오답2", "오답입니다", false)
        );
        Options options = new Options(optionRaws);

        // when & then
        Assertions.assertAll(
                () -> assertThat(options.isCorrectAnswer(List.of(1L))).isTrue(),
                () -> assertThat(options.isCorrectAnswer(List.of(2L))).isFalse(),
                () -> assertThat(options.isCorrectAnswer(List.of(3L))).isFalse()
        );
    }

    @DisplayName("정답 선지가 여러개인 경우 모두 일치하면 true를 반환한다")
    @Test
    void should_return_true_when_all_correct_answers() {
        // given
        List<Option> optionRaws = List.of(
                new Option(1L, 1L, "정답1", "정답입니다", true),
                new Option(2L, 1L, "정답2", "정답입니다", true),
                new Option(3L, 1L, "오답", "오답입니다", false)
        );
        Options options = new Options(optionRaws);

        // when & then
        Assertions.assertAll(
                () -> assertThat(options.isCorrectAnswer(List.of(1L, 2L))).isTrue(),
                () -> assertThat(options.isCorrectAnswer(List.of(1L, 3L))).isFalse(),
                () -> assertThat(options.isCorrectAnswer(List.of(2L, 3L))).isFalse()
        );
    }
}
