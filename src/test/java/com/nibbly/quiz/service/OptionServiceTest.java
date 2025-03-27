package com.nibbly.quiz.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import com.nibbly.global.supports.DatabaseCleaner;
import com.nibbly.quiz.Question;
import com.nibbly.quiz.domain.Option;
import com.nibbly.quiz.domain.OptionRepository;
import java.time.LocalDate;
import java.util.List;
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

    @BeforeEach
    void setUp() {
        databaseCleaner.executeTruncate();
    }

    @DisplayName("정답이 없는 선지 목록은 등록할 수 없다.")
    @Test
    void should_throw_exception_when_question_has_no_answer() {
        // given
        Question noAnswerQuestion = new Question("정답이 없는 문제", LocalDate.now().plusDays(1));
        Long questionId = questionService.saveQuestion(noAnswerQuestion);

        Option option1 = new Option(questionId, "오답1", false);
        Option option2 = new Option(questionId, "오답2", false);
        Option option3 = new Option(questionId, "오답3", false);
        Option option4 = new Option(questionId, "오답4", false);
        List<Option> noAnswerOptions = List.of(option1, option2, option3, option4);

        // when & then
        assertThatThrownBy(() -> optionService.saveOptions(noAnswerOptions))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("선지 내용이 중복되는 문제는 등록할 수 없다.")
    @Test
    void should_throw_exception_when_question_has_duplicate_options() {
        // given
        Question noAnswerQuestion = new Question("선지 내용이 중복되는 문제", LocalDate.now().plusDays(1));
        Long questionId = questionService.saveQuestion(noAnswerQuestion);

        Option option1 = new Option(questionId, "중복", false);
        Option option2 = new Option(questionId, "중복", false);
        Option option3 = new Option(questionId, "중복", false);
        Option option4 = new Option(questionId, "중복", true);
        List<Option> duplicatedOptions = List.of(option1, option2, option3, option4);

        // when & then
        assertThatThrownBy(() -> optionService.saveOptions(duplicatedOptions))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
