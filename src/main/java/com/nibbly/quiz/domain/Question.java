package com.nibbly.quiz;

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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String text;

    @Column(nullable = false)
    private LocalDate scheduleAt;

    public Question(Long id, String text, LocalDate scheduleAt) {
        this.id = id;
        this.text = text;
        this.scheduleAt = scheduleAt;
    }

    public Question(String text, LocalDate scheduleAt) {
        this(null, text, scheduleAt);
    }
}
