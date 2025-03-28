package com.nibbly.quiz.service;

import com.nibbly.global.supports.DatabaseCleaner;
import com.nibbly.quiz.domain.OptionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@DisplayName("OptionService 테스트")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class OptionServiceTest {

    @Autowired
    private OptionRepository optionRepository;
    @Autowired
    private OptionService optionService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private DatabaseCleaner databaseCleaner;

    @BeforeEach
    void setUp() {
        databaseCleaner.executeTruncate();
    }

}
