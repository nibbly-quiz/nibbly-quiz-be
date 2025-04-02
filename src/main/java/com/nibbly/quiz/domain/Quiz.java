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

    private static final int MAX_TITLE_LENGTH = 500;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "VARCHAR(500)", nullable = false)
    private String title;

    @Column(nullable = false)
    private LocalDate scheduledAt;

    public Quiz(Long id, String title, LocalDate scheduledAt) {
        validateTitleLength(title);
        this.id = id;
        this.title = title;
        this.scheduledAt = scheduledAt;
    }

    private void validateTitleLength(String text) {
        if (text.length() > MAX_TITLE_LENGTH) {
            throw new NibblyQuizException(ErrorCode.INVALID_TITLE_LENGTH);
        }
    }

    public Quiz(String title, LocalDate scheduledAt) {
        this(null, title, scheduledAt);
    }

    public boolean isScheduledBefore(LocalDate date) {
        return scheduledAt.isBefore(date);
    }
}
