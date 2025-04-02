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
    private QuestionService questionService;
    @Autowired
    private DatabaseCleaner databaseCleaner;

    private Long questionId;

    @BeforeEach
    void setUp() {
        databaseCleaner.executeTruncate();
        questionId = questionService.saveQuestion(QuizFixture.QUIZ.getQuestion());
    }

    @DisplayName("옵션을 등록할 수 있다.")
    @Test
    void should_create_option() {
        // given
        Options options = OptionFixture.getOptions(questionId, OptionFixture.ANSWER_1, OptionFixture.WRONG_1);

        // when & then
        assertThatCode(() -> optionService.saveOptions(options))
                .doesNotThrowAnyException();
    }

    @DisplayName("문제 ID로 옵션을 조회할 수 있다.")
    @Test
    void should_find_option_by_question_id() {
        // given
        Options options = OptionFixture.getOptions(questionId, OptionFixture.ANSWER_1, OptionFixture.WRONG_1);
        optionService.saveOptions(options);

        // when
        Options found = optionService.readOptions(questionId);

        // then
        assertThat(found.getOptionList()).hasSize(2);
    }
}
