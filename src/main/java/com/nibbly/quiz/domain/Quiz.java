package com.nibbly.quiz;

import com.nibbly.global.exception.ErrorCode;
import com.nibbly.global.exception.NibblyQuizException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Quiz {

    private static final int MAX_TITLE_LENGTH = 500;
    private static final int MAX_COMMENTARY_LENGTH = 5000;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "VARCHAR(500)", nullable = false)
    private String title;

    @Column(nullable = false)
    private LocalDate scheduledAt;

    @Lob
    @Column(nullable = false)
    private String commentary;

    public Quiz(Long id, String title, LocalDate scheduledAt, String commentary) {
        validateTitleLength(title);
        validateCommentaryLength(commentary);
        this.id = id;
        this.title = title;
        this.scheduledAt = scheduledAt;
        this.commentary = commentary;
    }

    private void validateTitleLength(String text) {
        if (text.length() > MAX_TITLE_LENGTH) {
            throw new NibblyQuizException(ErrorCode.INVALID_TITLE_LENGTH);
        }
    }

    private void validateCommentaryLength(String commentary) {
        if (commentary.length() > MAX_COMMENTARY_LENGTH) {
            throw new NibblyQuizException(ErrorCode.INVALID_COMMENTARY_LENGTH);
        }
    }

    public Quiz(String title, LocalDate scheduledAt, String commentary) {
        this(null, title, scheduledAt, commentary);
    }

    public boolean isScheduledBefore(LocalDate date) {
        return scheduledAt.isBefore(date);
    }
}
