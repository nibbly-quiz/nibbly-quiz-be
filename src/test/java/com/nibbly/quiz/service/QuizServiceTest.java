package com.nibbly.quiz.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import com.nibbly.quiz.domain.OptionRepository;
import com.nibbly.quiz.domain.QuestionRepository;
import com.nibbly.quiz.dto.OptionCreateRequest;
import com.nibbly.quiz.dto.QuestionCreateRequest;
import com.nibbly.quiz.dto.QuizCreateRequest;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@DisplayName("퀴즈 서비스 테스트")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class QuizServiceTest {

    @Autowired
    private QuizService quizService;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private OptionRepository optionRepository;

    @DisplayName("퀴즈를 생성할 수 있다.")
    @Test
    void createQuiz() {
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
        quizService.saveQuiz(request);

        // then
        assertThat(questionRepository.findAll()).hasSize(1);
        assertThat(optionRepository.findAll()).hasSize(4);
    }

    @DisplayName("과거 일자에 대한 문제는 등록할 수 없다.")
    @Test
    void should_throw_exception_when_question_is_scheduled_before_today() {
        // given
        QuestionCreateRequest questionCreateRequest = new QuestionCreateRequest("과거 일자에 대한 문제",
                LocalDate.now().minusDays(1));
        OptionCreateRequest optionCreateRequest = new OptionCreateRequest("정답", true);
        QuizCreateRequest request = new QuizCreateRequest(questionCreateRequest, List.of(optionCreateRequest));

        // when, then
        assertThatThrownBy(() -> quizService.saveQuiz(request))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("정답이 없는 문제는 등록할 수 없다.")
    @Test
    void should_throw_exception_when_question_has_no_answer() {
        // given
        QuestionCreateRequest questionCreateRequest = new QuestionCreateRequest("정답이 없는 문제",
                LocalDate.now().plusDays(1));
        OptionCreateRequest optionCreateRequest = new OptionCreateRequest("오답", false);
        QuizCreateRequest request = new QuizCreateRequest(questionCreateRequest, List.of(optionCreateRequest));

        // when, then
        assertThatThrownBy(() -> quizService.saveQuiz(request))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("선지 내용이 중복되는 문제는 등록할 수 없다.")
    @Test
    void should_throw_exception_when_question_has_duplicate_options() {
        // given
        QuestionCreateRequest questionCreateRequest = new QuestionCreateRequest("선지 내용이 중복되는 문제",
                LocalDate.now().plusDays(1));
        OptionCreateRequest optionCreateRequest1 = new OptionCreateRequest("중복", false);
        OptionCreateRequest optionCreateRequest2 = new OptionCreateRequest("중복", false);
        QuizCreateRequest request = new QuizCreateRequest(questionCreateRequest,
                List.of(optionCreateRequest1, optionCreateRequest2));

        // when, then
        assertThatThrownBy(() -> quizService.saveQuiz(request))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
