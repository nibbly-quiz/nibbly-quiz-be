package com.nibbly.quiz.domain;


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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long questionId;

    @Column(nullable = false)
    private String text;

    @Column(nullable = false)
    private boolean isAnswer;

    public Option(Long id, Long questionId, String text, boolean isAnswer) {
        this.id = id;
        this.questionId = questionId;
        this.text = text;
        this.isAnswer = isAnswer;
    }

    public Option(Long questionId, String text, boolean isAnswer) {
        this(null, questionId, text, isAnswer);
    }
}
