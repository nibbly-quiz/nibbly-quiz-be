package com.nibbly.quiz.repository;

import com.nibbly.quiz.Quiz;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {
    List<Quiz> findByScheduledAt(LocalDate scheduleAt);
}
