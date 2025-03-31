package com.nibbly.quiz.service;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import com.nibbly.global.exception.NibblyQuizException;
import com.nibbly.global.supports.DatabaseCleaner;
import com.nibbly.quiz.Question;
import com.nibbly.quiz.repository.QuestionRepository;
import java.time.LocalDate;
import java.util.List;
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

    @DisplayName("오늘 출제될 문제의 ID 목록을 조회할 수 있다.")
    @Test
    void should_find_question_ids_scheduled_today() {
        // given
        Question question1 = new Question("오늘 출제될 문제1", LocalDate.now());
        Question question2 = new Question("오늘 출제될 문제2", LocalDate.now());
        Question question3 = new Question("내일 출제될 문제", LocalDate.now().plusDays(1));
        questionRepository.save(question1);
        questionRepository.save(question2);
        questionRepository.save(question3);

        // when
        List<Long> questionIds = questionService.readQuestionIdsScheduledToday();

        // then
        assertThat(questionIds).containsExactlyInAnyOrder(question1.getId(), question2.getId());
    }
}
