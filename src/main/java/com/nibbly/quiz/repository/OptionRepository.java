package com.nibbly.quiz.repository;

import com.nibbly.quiz.domain.Option;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OptionRepository extends JpaRepository<Option, Long> {
    List<Option> findByQuizId(Long quizId);

    @Query("SELECT o FROM Option o WHERE o.quizId IN :quizIds AND o.isAnswer = true")
    List<Option> findAnswerOptions(@Param("quizIds") Set<Long> quizIds);
}
