package com.nibbly.quiz.domain;


import com.nibbly.global.exception.ErrorCode;
import com.nibbly.global.exception.NibblyQuizException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Option {

    private static final int MAX_CONTENT_LENGTH = 100;
    private static final int MAX_CORRECTION_LENGTH = 100;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long quizId;

    @Column(columnDefinition = "VARCHAR(100)", nullable = false)
    private String content;

    @Column(nullable = true)
    private String correction;

    @Column(nullable = false)
    private boolean isAnswer;

    public Option(Long id, Long quizId, String content, String correction, boolean isAnswer) {
        validateContentLength(content);
        validateCorrectionLength(correction);
        validateCorrection(isAnswer, correction);
        this.id = id;
        this.quizId = quizId;
        this.content = content;
        this.correction = correction;
        this.isAnswer = isAnswer;
    }

    private void validateContentLength(String content) {
        if (content.length() > MAX_CONTENT_LENGTH) {
            throw new NibblyQuizException(ErrorCode.INVALID_OPTION_LENGTH);
        }
    }

    private void validateCorrectionLength(String correction) {
        if (correction != null && correction.length() > MAX_CORRECTION_LENGTH) {
            throw new NibblyQuizException(ErrorCode.INVALID_CORRECTION_LENGTH);
        }
    }

    private void validateCorrection(boolean isAnswer, String correction) {
        if (!isAnswer && StringUtils.isBlank(correction)) {
            throw new NibblyQuizException(ErrorCode.CORRECTION_REQUIRED);
        }
    }

    public Option(Long quizId, String content, String correction, boolean isAnswer) {
        this(null, quizId, content, correction, isAnswer);
    }
}
