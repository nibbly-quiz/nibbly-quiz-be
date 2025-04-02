package com.nibbly.quiz.dto.request;

import com.nibbly.quiz.Quiz;
import com.nibbly.quiz.domain.Options;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

public record QuizCreateRequest(
        @NotBlank(message = "질문 내용은 비어있을 수 없습니다.")
        String text,
        @NotNull(message = "출제 날짜는 비어있을 수 없습니다.")
        LocalDate scheduleAt,
        @NotNull(message = "선지는 비어있을 수 없습니다")
        @Valid
        List<OptionCreateRequest> optionCreateRequests
) {
    public Quiz getQuiz() {
        return new Quiz(text, scheduleAt);
    }

    public Options getOptions(Long questionId) {
        return new Options(optionCreateRequests.stream()
                .map(optionCreateRequest -> optionCreateRequest.toEntity(questionId))
                .toList());
    }
}
