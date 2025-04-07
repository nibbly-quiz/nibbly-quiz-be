package com.nibbly.quiz.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.nibbly.quiz.fixture.OptionFixture;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("AnswerSheet 테스트")
class AnswerSheetTest {

    @DisplayName("유저 제출 답안을 받아 정답 개수를 반환할 수 있다")
    @Test
    void should_return_correct_count() {
        // given
        Option firstQuizAnswerOption = OptionFixture.ANSWER_1.getOption(1L, 1L);
        Option secondQuizAnswerOption = OptionFixture.ANSWER_2.getOption(2L, 2L);

        AnswerSheet answerSheet = AnswerSheet.from(List.of(firstQuizAnswerOption, secondQuizAnswerOption));

        Map<Long, Set<Long>> userAnswers = Map.of(
                1L, Set.of(1L),
                2L, Set.of(3L)
        );

        // when
        int correctCount = answerSheet.countCorrect(userAnswers);

        // then
        assertThat(correctCount).isOne();
    }
}
