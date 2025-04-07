package com.nibbly.quiz.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import com.nibbly.global.supports.DatabaseCleaner;
import com.nibbly.quiz.domain.Option;
import com.nibbly.quiz.domain.Options;
import com.nibbly.quiz.fixture.OptionFixture;
import com.nibbly.quiz.fixture.QuizFixture;
import com.nibbly.quiz.repository.OptionRepository;
import java.util.Set;
import java.util.stream.Collectors;
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
        quizId = quizService.saveQuiz(QuizFixture.QUIZ.getQuiz()).getId();
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
        Options found = optionService.findOptions(quizId);

        // then
        assertThat(found.getOptionList()).hasSize(2);
    }

    @DisplayName("퀴즈 ID와 선지 ID를 통해 정답 여부를 확인할 수 있다.")
    @Test
    void should_check_correct_answer() {
        // given
        Options options = OptionFixture.getOptions(quizId, OptionFixture.ANSWER_1, OptionFixture.WRONG_1);
        optionService.saveOptions(options);
        Set<Long> answers = optionService.findOptions(quizId).getOptionList()
                .stream()
                .filter(Option::isAnswer)
                .map(Option::getId)
                .collect(Collectors.toSet());

        // when
        boolean isCorrect = optionService.isCorrectAnswer(quizId, answers);

        // then
        assertThat(isCorrect).isTrue();
    }
}
