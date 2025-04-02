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

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Option {

    private static final int MAX_TEXT_LENGTH = 100;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long questionId;

    @Column(columnDefinition = "VARCHAR(100)", nullable = false)
    private String content;

    @Column(nullable = false)
    private boolean isAnswer;

    public Option(Long id, Long questionId, String content, boolean isAnswer) {
        validateTextLength(content);
        this.id = id;
        this.questionId = questionId;
        this.content = content;
        this.isAnswer = isAnswer;
    }

    private void validateTextLength(String text) {
        if (text.length() > MAX_TEXT_LENGTH) {
            throw new NibblyQuizException(ErrorCode.INVALID_OPTION_LENGTH);
        }
    }

    public Option(Long questionId, String content, boolean isAnswer) {
        this(null, questionId, content, isAnswer);
    }
}
