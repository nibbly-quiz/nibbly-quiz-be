package com.nibbly.quiz.service;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import com.nibbly.global.supports.DatabaseCleaner;
import com.nibbly.quiz.dto.request.OptionCreateRequest;
import com.nibbly.quiz.dto.request.QuestionCreateRequest;
import com.nibbly.quiz.dto.request.QuizCreateRequest;
import com.nibbly.quiz.dto.response.QuizResponse;
import com.nibbly.quiz.repository.OptionRepository;
import com.nibbly.quiz.repository.QuestionRepository;
import java.time.LocalDate;
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
    private QuestionRepository questionRepository;
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
        QuestionCreateRequest questionCreateRequest = new QuestionCreateRequest("인덱스에 대한 설명으로 바르지 않은 것은?",
                LocalDate.now().plusDays(1));

        OptionCreateRequest optionCreateRequest1 = new OptionCreateRequest("데이터베이스의 기본키로 사용할 수 있다.", false);
        OptionCreateRequest optionCreateRequest2 = new OptionCreateRequest("중복을 허용한다.", false);
        OptionCreateRequest optionCreateRequest3 = new OptionCreateRequest("NULL 값을 가질 수 있다.", false);
        OptionCreateRequest optionCreateRequest4 = new OptionCreateRequest("인덱스를 생성하면 해당 컬럼의 데이터가 정렬된다.", true);

        QuizCreateRequest request = new QuizCreateRequest(questionCreateRequest,
                List.of(optionCreateRequest1, optionCreateRequest2,
                        optionCreateRequest3, optionCreateRequest4));

        // when
        quizFacadeService.saveQuiz(request);

        // then
        Assertions.assertAll(
                () -> assertThat(questionRepository.findAll()).hasSize(1),
                () -> assertThat(optionRepository.findAll()).hasSize(4)
        );
    }

    @DisplayName("오늘 출제될 문제의 ID 목록을 조회할 수 있다.")
    @Test
    void should_find_question_ids_scheduled_today() {
        // given
        QuestionCreateRequest questionCreateRequest1 = new QuestionCreateRequest("오늘 출제될 문제1", LocalDate.now());
        QuestionCreateRequest questionCreateRequest2 = new QuestionCreateRequest("오늘 출제될 문제2", LocalDate.now());
        QuestionCreateRequest questionCreateRequest3 = new QuestionCreateRequest("내일 출제될 문제",
                LocalDate.now().plusDays(1));

        List<OptionCreateRequest> optionsCreateRequest = List.of(
                new OptionCreateRequest("정답", true),
                new OptionCreateRequest("오답", false)
        );
        QuizCreateRequest request1 = new QuizCreateRequest(questionCreateRequest1, optionsCreateRequest);
        QuizCreateRequest request2 = new QuizCreateRequest(questionCreateRequest2, optionsCreateRequest);
        QuizCreateRequest request3 = new QuizCreateRequest(questionCreateRequest3, optionsCreateRequest);

        quizFacadeService.saveQuiz(request1);
        quizFacadeService.saveQuiz(request2);
        quizFacadeService.saveQuiz(request3);

        // when
        List<Long> questionIdsScheduledToday = quizFacadeService.getQuestionsScheduledToday().quizIds();

        // then
        assertThat(questionIdsScheduledToday).hasSize(2);
    }

    @DisplayName("ID를 기반으로 퀴즈를 조회할 수 있다.")
    @Test
    void should_find_quiz_by_id() {
        // given
        QuestionCreateRequest questionCreateRequest = new QuestionCreateRequest("문제", LocalDate.now());
        OptionCreateRequest optionCreateRequest1 = new OptionCreateRequest("정답", true);
        OptionCreateRequest optionCreateRequest2 = new OptionCreateRequest("오답", false);
        QuizCreateRequest request = new QuizCreateRequest(questionCreateRequest,
                List.of(optionCreateRequest1, optionCreateRequest2));

        Long quizId = quizFacadeService.saveQuiz(request);

        // when
        QuizResponse quiz = quizFacadeService.getQuiz(quizId);

        // then
        assertThat(quiz.question().text()).isEqualTo("문제");
    }
}
