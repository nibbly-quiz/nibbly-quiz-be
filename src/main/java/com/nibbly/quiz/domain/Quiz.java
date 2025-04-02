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
public class Quiz {

    private static final int MAX_TEXT_LENGTH = 500;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "VARCHAR(500)", nullable = false)
    private String text;

    @Column(nullable = false)
    private LocalDate scheduledAt;

    public Quiz(Long id, String text, LocalDate scheduledAt) {
        validateTextLength(text);
        this.id = id;
        this.text = text;
        this.scheduledAt = scheduledAt;
    }

    private void validateTextLength(String text) {
        if (text.length() > MAX_TEXT_LENGTH) {
            throw new NibblyQuizException(ErrorCode.INVALID_QUESTION_LENGTH);
        }
    }

    public Quiz(String text, LocalDate scheduledAt) {
        this(null, text, scheduledAt);
    }

    public boolean isScheduledBefore(LocalDate date) {
        return scheduledAt.isBefore(date);
    }
}
