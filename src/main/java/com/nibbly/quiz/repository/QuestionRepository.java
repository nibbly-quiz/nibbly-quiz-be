package com.nibbly.quiz.repository;

import com.nibbly.quiz.Question;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByScheduledAt(LocalDate scheduleAt);
}
