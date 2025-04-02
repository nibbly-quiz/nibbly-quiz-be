package com.nibbly.quiz.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import com.nibbly.global.supports.DatabaseCleaner;
import com.nibbly.quiz.domain.Options;
import com.nibbly.quiz.fixture.OptionFixture;
import com.nibbly.quiz.fixture.QuizFixture;
import com.nibbly.quiz.repository.OptionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@DisplayName("OptionService 테스트")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class OptionServiceTest {

    @Autowired
    private OptionRepository optionRepository;
    @Autowired
    private OptionService optionService;
    @Autowired
    private QuizService quizService;
    @Autowired
    private DatabaseCleaner databaseCleaner;

    private Long quizId;

    @BeforeEach
    void setUp() {
        databaseCleaner.executeTruncate();
        quizId = quizService.saveQuiz(QuizFixture.QUIZ.getQuiz());
    }

    @DisplayName("옵션을 등록할 수 있다.")
    @Test
    void should_create_option() {
        // given
        Options options = OptionFixture.getOptions(quizId, OptionFixture.ANSWER_1, OptionFixture.WRONG_1);

        // when & then
        assertThatCode(() -> optionService.saveOptions(options))
                .doesNotThrowAnyException();
    }

    @DisplayName("문제 ID로 옵션을 조회할 수 있다.")
    @Test
    void should_find_option_by_quiz_id() {
        // given
        Options options = OptionFixture.getOptions(quizId, OptionFixture.ANSWER_1, OptionFixture.WRONG_1);
        optionService.saveOptions(options);

        // when
        Options found = optionService.readOptions(quizId);

        // then
        assertThat(found.getOptionList()).hasSize(2);
    }
}
