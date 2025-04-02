package com.nibbly.quiz.service;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import com.nibbly.global.supports.DatabaseCleaner;
import com.nibbly.quiz.dto.request.QuizCreateRequest;
import com.nibbly.quiz.dto.response.QuizToSolveResponse;
import com.nibbly.quiz.fixture.QuizFixture;
import com.nibbly.quiz.repository.OptionRepository;
import com.nibbly.quiz.repository.QuizRepository;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@DisplayName("퀴즈 서비스 테스트")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class QuizFacadeServiceTest {

    @Autowired
    private QuizFacadeService quizFacadeService;
    @Autowired
    private QuizRepository quizRepository;
    @Autowired
    private OptionRepository optionRepository;
    @Autowired
    private DatabaseCleaner databaseCleaner;

    @BeforeEach
    void setUp() {
        databaseCleaner.executeTruncate();
    }

    @DisplayName("퀴즈를 생성할 수 있다.")
    @Test
    void should_enroll_quiz_when_requested() {
        // given
        QuizCreateRequest quizCreateRequest = QuizFixture.QUIZ.getQuizCreateRequest();

        // when
        quizFacadeService.saveQuiz(quizCreateRequest);

        // then
        Assertions.assertAll(
                () -> assertThat(quizRepository.findAll()).hasSize(1),
                () -> assertThat(optionRepository.findAll()).hasSize(quizCreateRequest.optionCreateRequests().size())
        );
    }

    @DisplayName("오늘 출제될 문제의 ID 목록을 조회할 수 있다.")
    @Test
    void should_find_quiz_ids_scheduled_today() {
        // given
        QuizCreateRequest quizCreateRequest = QuizFixture.QUIZ.getQuizCreateRequest();
        quizFacadeService.saveQuiz(quizCreateRequest);

        // when
        List<Long> quizIdsScheduledToday = quizFacadeService.getQuizzesScheduledToday().quizIds();

        // then
        assertThat(quizIdsScheduledToday).hasSize(1);
    }

    @DisplayName("ID를 기반으로 퀴즈를 조회할 수 있다.")
    @Test
    void should_find_quiz_by_id() {
        // given
        QuizCreateRequest quizCreateRequest = QuizFixture.QUIZ.getQuizCreateRequest();
        Long quizId = quizFacadeService.saveQuiz(quizCreateRequest);

        // when
        QuizToSolveResponse quizToSolveResponse = quizFacadeService.getQuiz(quizId);

        // then
        assertThat(quizToSolveResponse.title()).isEqualTo(quizCreateRequest.title());
    }
}
