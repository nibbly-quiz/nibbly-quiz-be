package com.nibbly.quiz.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.nibbly.quiz.Quiz;
import com.nibbly.quiz.domain.Option;
import com.nibbly.quiz.fixture.OptionFixture;
import com.nibbly.quiz.fixture.QuizFixture;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DisplayName("OptionRepository 테스트")
@DataJpaTest
class OptionRepositoryTest {

    @Autowired
    private OptionRepository optionRepository;
    @Autowired
    private QuizRepository quizRepository;

    @DisplayName("옵션을 문제 ID로 조회할 수 있다.")
    @Test
    void should_find_option_by_quiz_id() {
        // given
        Quiz quiz = quizRepository.save(QuizFixture.QUIZ.getQuiz());
        Long quizId = quiz.getId();

        Option option1 = OptionFixture.ANSWER_1.getOption(quizId);
        Option option2 = OptionFixture.WRONG_1.getOption(quizId);
        Option option3 = OptionFixture.WRONG_2.getOption(quizId);
        Option option4 = OptionFixture.WRONG_3.getOption(quizId);
        List<Option> options = List.of(option1, option2, option3, option4);
        optionRepository.saveAll(options);

        // when
        List<Option> found = optionRepository.findByQuizId(quizId);

        // then
        assertThat(found).hasSize(options.size());
    }
}
