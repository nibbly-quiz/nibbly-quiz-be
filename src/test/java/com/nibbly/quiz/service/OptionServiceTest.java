package com.nibbly.quiz.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

import com.nibbly.global.supports.DatabaseCleaner;
import com.nibbly.quiz.Question;
import com.nibbly.quiz.domain.Option;
import com.nibbly.quiz.domain.OptionRepository;
import com.nibbly.quiz.domain.Options;
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

    @DisplayName("옵션을 등록할 수 있다.")
    @Test
    void should_create_option() {
        // given
        Long questionId = questionService.saveQuestion(new Question("문제", LocalDate.now()));
        Option option1 = new Option(questionId, "정답", true);
        Option option2 = new Option(questionId, "오답", false);
        Options options = new Options(List.of(option1, option2));

        // when & then
        assertThatCode(() -> optionService.saveOptions(options))
                .doesNotThrowAnyException();
    }
}
