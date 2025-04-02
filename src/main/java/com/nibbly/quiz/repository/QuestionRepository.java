package com.nibbly.quiz.repository;

import com.nibbly.quiz.Quiz;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Quiz, Long> {
    @Query("SELECT q.id FROM Quiz q WHERE q.scheduledAt = :scheduleAt")
    List<Long> findByScheduledAt(@Param("scheduleAt") LocalDate scheduleAt);
}
