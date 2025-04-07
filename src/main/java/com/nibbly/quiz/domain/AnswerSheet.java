package com.nibbly.quiz.domain;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class AnswerSheet {

    private final Map<Long, Set<Long>> quizToAnswer;

    public AnswerSheet(Map<Long, Set<Long>> quizToAnswer) {
        this.quizToAnswer = quizToAnswer;
    }

    public static AnswerSheet from(List<Option> quizAnswers) {
        Map<Long, Set<Long>> quizToAnswer = quizAnswers.stream()
                .collect(Collectors.groupingBy(Option::getQuizId,
                        Collectors.mapping(Option::getId, Collectors.toSet())));
        return new AnswerSheet(quizToAnswer);
    }

    public int countCorrect(Map<Long, Set<Long>> userAnswers) {
        return (int) userAnswers.entrySet().stream()
                .filter(entry -> quizToAnswer.get(entry.getKey()).equals(entry.getValue()))
                .count();
    }
}
