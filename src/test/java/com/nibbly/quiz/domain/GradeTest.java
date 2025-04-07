package com.nibbly.quiz.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("Grade 테스트")
class GradeTest {

    private static final int QUIZ_COUNT = 100;

    @DisplayName("85프로 이상의 정답율을 기록한 경우 A+가 Rank 된다")
    @ParameterizedTest
    @ValueSource(ints = {85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100})
    void should_return_rank(int correctCount) {
        // given
        Grade grade = new Grade(QUIZ_COUNT, correctCount);

        // when & then
        assertThat(grade.getRankDisplayName()).isEqualTo("A+");
    }

    @DisplayName("70프로 이상의 정답율을 기록한 경우 A가 Rank 된다")
    @ParameterizedTest
    @ValueSource(ints = {70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84})
    void should_return_rank_A(int correctCount) {
        // given
        Grade grade = new Grade(QUIZ_COUNT, correctCount);

        // when & then
        assertThat(grade.getRankDisplayName()).isEqualTo("A");
    }

    @DisplayName("55프로 이상의 정답율을 기록한 경우 B+가 Rank 된다")
    @ParameterizedTest
    @ValueSource(ints = {55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69})
    void should_return_rank_B_PLUS(int correctCount) {
        // given
        Grade grade = new Grade(QUIZ_COUNT, correctCount);

        // when & then
        assertThat(grade.getRankDisplayName()).isEqualTo("B+");
    }

    @DisplayName("40프로 이상의 정답율을 기록한 경우 B가 Rank 된다")
    @ParameterizedTest
    @ValueSource(ints = {40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54})
    void should_return_rank_B(int correctCount) {
        // given
        Grade grade = new Grade(QUIZ_COUNT, correctCount);

        // when & then
        assertThat(grade.getRankDisplayName()).isEqualTo("B");
    }

    @DisplayName("25프로 이상의 정답율을 기록한 경우 C가 Rank 된다")
    @ParameterizedTest
    @ValueSource(ints = {25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39})
    void should_return_rank_C(int correctCount) {
        // given
        Grade grade = new Grade(QUIZ_COUNT, correctCount);

        // when & then
        assertThat(grade.getRankDisplayName()).isEqualTo("C");
    }

    @DisplayName("25프로 미만의 정답율을 기록한 경우 F가 Rank 된다")
    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14})
    void should_return_rank_F(int correctCount) {
        // given
        Grade grade = new Grade(QUIZ_COUNT, correctCount);

        // when & then
        assertThat(grade.getRankDisplayName()).isEqualTo("F");
    }
}
