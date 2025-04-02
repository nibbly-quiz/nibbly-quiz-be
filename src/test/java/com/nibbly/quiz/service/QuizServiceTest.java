package com.nibbly.quiz.service;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import com.nibbly.global.exception.NibblyQuizException;
import com.nibbly.global.supports.DatabaseCleaner;
import com.nibbly.quiz.Quiz;
import com.nibbly.quiz.fixture.QuizFixture;
import com.nibbly.quiz.repository.QuizRepository;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@DisplayName("QuizService 테스트")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class QuizServiceTest {

    @Autowired
    private QuizService quizService;
    @Autowired
    private QuizRepository quizRepository;
    @Autowired
    private DatabaseCleaner databaseCleaner;

    @BeforeEach
    void setUp() {
        databaseCleaner.executeTruncate();
    }

    @DisplayName("문제를 등록할 수 있다.")
    @Test
    void should_create_quiz() {
        // given
        Quiz quiz = QuizFixture.QUIZ.getQuiz();

        // when & then
        assertThatCode(() -> quizService.saveQuiz(quiz))
                .doesNotThrowAnyException();
    }

    @DisplayName("과거 일자에 대한 문제는 등록할 수 없다.")
    @Test
    void should_throw_exception_when_quiz_is_scheduled_before_today() {
        // given
        Quiz yesterdayQuiz = QuizFixture.QUIZ.getQuizScheduledAt(LocalDate.now().minusDays(1));

        // when & then
        assertThatThrownBy(() -> quizService.saveQuiz(yesterdayQuiz))
                .isInstanceOf(NibblyQuizException.class)
                .hasMessage("과거 일자에 대한 문제는 등록할 수 없습니다");
    }

    @DisplayName("오늘 출제될 문제의 ID 목록을 조회할 수 있다.")
    @Test
    void should_find_quiz_ids_scheduled_today() {
        // given
        Quiz todayQuiz = QuizFixture.QUIZ.getQuizScheduledAt(LocalDate.now());
        quizRepository.save(todayQuiz);

        // when
        List<Long> quizIds = quizService.readQuizzesScheduledToday();

        // then
        assertThat(quizIds).containsExactly(todayQuiz.getId());
    }

    @DisplayName("문제 ID로 문제를 조회할 수 있다.")
    @Test
    void should_find_quiz_by_id() {
        // given
        Quiz quiz = QuizFixture.QUIZ.getQuiz();
        Long quizId = quizRepository.save(quiz).getId();

        // when
        Quiz found = quizService.readQuiz(quizId);

        // then
        assertThatCode(() -> quizService.readQuiz(quizId))
                .doesNotThrowAnyException();
    }

    @DisplayName("존재하지 않는 문제 ID로 문제를 조회하면 예외가 발생한다.")
    @Test
    void should_throw_exception_when_quiz_not_found() {
        // given
        Long noExistQuizId = 1L;

        // when & then
        assertThatThrownBy(() -> quizService.readQuiz(noExistQuizId))
                .isInstanceOf(NibblyQuizException.class)
                .hasMessage("문제를 찾을 수 없습니다");
    }
}
