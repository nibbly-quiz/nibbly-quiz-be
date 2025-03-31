package com.nibbly.quiz.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.nibbly.quiz.Question;
import com.nibbly.quiz.domain.Option;
import java.time.LocalDate;
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
    private QuestionRepository questionRepository;

    @DisplayName("옵션을 문제 ID로 조회할 수 있다.")
    @Test
    void should_find_option_by_question_id() {
        // given
        Long questionId = questionRepository.save(new Question("문제", LocalDate.now())).getId();
        List<Option> options = List.of(new Option(questionId, "정답", true), new Option(questionId, "오답", false));
        optionRepository.saveAll(options);

        // when
        List<Option> found = optionRepository.findByQuestionId(questionId);

        // then
        assertThat(found).hasSize(2);
    }
}
