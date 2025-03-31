package com.nibbly.quiz;

import com.nibbly.global.exception.ErrorCode;
import com.nibbly.global.exception.NibblyQuizException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Question {

    private static final int MAX_TEXT_LENGTH = 500;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "VARCHAR(500)", nullable = false)
    private String text;

    @Column(nullable = false)
    private LocalDate scheduleAt;

    public Question(Long id, String text, LocalDate scheduleAt) {
        validateTextLength(text);
        this.id = id;
        this.text = text;
        this.scheduleAt = scheduleAt;
    }

    private void validateTextLength(String text) {
        if (text.length() > MAX_TEXT_LENGTH) {
            throw new NibblyQuizException(ErrorCode.INVALID_QUESTION_LENGTH);
        }
    }

    public Question(String text, LocalDate scheduleAt) {
        this(null, text, scheduleAt);
    }

    public boolean isScheduledBefore(LocalDate date) {
        return scheduleAt.isBefore(date);
    }
}
