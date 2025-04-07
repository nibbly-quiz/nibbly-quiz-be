package com.nibbly.quiz.domain;

import com.nibbly.global.exception.ErrorCode;
import com.nibbly.global.exception.NibblyQuizException;
import java.util.Arrays;
import java.util.function.Predicate;
import lombok.Getter;

@Getter
public class Grade {

    private final int quizCount;
    private final int correctCount;
    private final Rank rank;

    public Grade(int quizCount, int correctCount) {
        this.quizCount = quizCount;
        this.correctCount = correctCount;
        this.rank = Rank.fromScore(quizCount, correctCount);
    }

    public String getRankDisplayName() {
        return rank.displayName;
    }

    private enum Rank {
        A_PLUS("A+", ratio -> ratio >= Rank.A_PLUS_MINIMUM),
        A("A", ratio -> ratio >= Rank.A_MINIMUM && ratio < Rank.A_PLUS_MINIMUM),
        B_PLUS("B+", ratio -> ratio >= Rank.B_PLUS_MINIMUM && ratio < Rank.A_MINIMUM),
        B("B", ratio -> ratio >= Rank.B_MINIMUM && ratio < Rank.B_PLUS_MINIMUM),
        C("C", ratio -> ratio >= Rank.C_MINIMUM && ratio < Rank.B_MINIMUM),
        F("F", ratio -> ratio < Rank.C_MINIMUM);


        private static final double A_PLUS_MINIMUM = 0.85;
        private static final double A_MINIMUM = 0.70;
        private static final double B_PLUS_MINIMUM = 0.55;
        private static final double B_MINIMUM = 0.40;
        private static final double C_MINIMUM = 0.25;
        private static final double F_MINIMUM = 0.0;

        private final String displayName;
        private final Predicate<Double> condition;

        Rank(String displayName, Predicate<Double> condition) {
            this.displayName = displayName;
            this.condition = condition;
        }

        public static Rank fromScore(int total, int correct) {
            return Arrays.stream(Rank.values())
                    .filter(rank -> rank.condition.test((double) correct / total))
                    .findFirst()
                    .orElseThrow(() -> new NibblyQuizException(ErrorCode.INTERNAL_SERVER_ERROR));
        }
    }
}
