package com.nibbly.quiz.dto.response;

import com.nibbly.quiz.domain.Option;

public record OptionResponse(
        Long id,
        String content
) {
    public static OptionResponse from(Option option) {
        return new OptionResponse(option.getId(), option.getContent());
    }
}
