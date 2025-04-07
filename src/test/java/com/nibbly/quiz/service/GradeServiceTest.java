package com.nibbly.quiz.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.nibbly.quiz.Quiz;
import com.nibbly.quiz.domain.Grade;
import com.nibbly.quiz.domain.Option;
import com.nibbly.quiz.domain.Options;
import com.nibbly.quiz.fixture.OptionFixture;
import com.nibbly.quiz.fixture.QuizFixture;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@DisplayName("채점 서비스 테스트")
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class GradeServiceTest {

    @Autowired
    private GradeService gradeService;
    @Autowired
    private QuizService quizService;
    @Autowired
    private OptionService optionService;

    private Long quizId;
    private Set<Long> answerOptionIds;

    @BeforeEach
    void setUp() {
        Quiz quiz = quizService.saveQuiz(QuizFixture.QUIZ.getQuiz());
        quizId = quiz.getId();

        Options options = OptionFixture.getOptions(quizId, OptionFixture.ANSWER_1, OptionFixture.WRONG_1);
        Options savedOptions = optionService.saveOptions(options);

        answerOptionIds = savedOptions.getOptionList().stream()
                .filter(Option::isAnswer)
                .map(Option::getId)
                .collect(Collectors.toSet());
    }

    @DisplayName("퀴즈 아이디와 제출 답안을 받아 퀴즈를 채점할 수 있다.")
    @Test
    void should_grade_quiz() {
        // given
        Map<Long, Set<Long>> userAnswer = new HashMap<>();
        userAnswer.put(quizId, answerOptionIds);

        // when
        Grade grade = gradeService.gradeUserAnswers(userAnswer);

        // then
        Assertions.assertAll(
                () -> assertThat(grade.getQuizCount()).isOne(),
                () -> assertThat(grade.getCorrectCount()).isOne()
        );
    }
}
