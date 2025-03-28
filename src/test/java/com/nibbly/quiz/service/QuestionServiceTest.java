package com.nibbly.quiz.service;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import com.nibbly.global.supports.DatabaseCleaner;
import com.nibbly.quiz.Question;
import com.nibbly.quiz.domain.QuestionRepository;
import com.nibbly.quiz.global.exception.NibblyQuizException;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@DisplayName("QuestionService 테스트")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class QuestionServiceTest {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private DatabaseCleaner databaseCleaner;

    @BeforeEach
    void setUp() {
        databaseCleaner.executeTruncate();
    }

    @DisplayName("문제를 등록할 수 있다.")
    @Test
    void should_create_question() {
        // given
        Question question = new Question("정상적인 문제", LocalDate.now().plusDays(1));

        // when & then
        assertThatCode(() -> questionService.saveQuestion(question))
                .doesNotThrowAnyException();
    }

    @DisplayName("과거 일자에 대한 문제는 등록할 수 없다.")
    @Test
    void should_throw_exception_when_question_is_scheduled_before_today() {
        // given
        Question question = new Question("출제일이 어제인 문제", LocalDate.now().minusDays(1));

        // when & then
        assertThatThrownBy(() -> questionService.saveQuestion(question))
                .isInstanceOf(NibblyQuizException.class)
                .hasMessage("과거 일자에 대한 문제는 등록할 수 없습니다");
    }
}
