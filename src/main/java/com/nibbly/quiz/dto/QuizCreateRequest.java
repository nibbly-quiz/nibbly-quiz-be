package com.nibbly.quiz.dto;

import jakarta.validation.constraints.NotNull;
import java.util.List;

public record QuizCreateRequest(
        QuestionCreateRequest questionRequest,
        List<OptionCreateRequest> optionCreateRequests
) {
}
