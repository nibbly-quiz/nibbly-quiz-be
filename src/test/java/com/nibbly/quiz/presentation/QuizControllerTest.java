package com.nibbly.quiz.presentation;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import com.nibbly.global.supports.DatabaseCleaner;
import com.nibbly.quiz.domain.Option;
import com.nibbly.quiz.dto.request.QuizCreateRequest;
import com.nibbly.quiz.dto.request.QuizzesSubmitRequest;
import com.nibbly.quiz.dto.request.QuizzesSubmitRequest.QuizSubmitRequest;
import com.nibbly.quiz.fixture.QuizFixture;
import com.nibbly.quiz.repository.OptionRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class QuizControllerTest {

    @Autowired
    private DatabaseCleaner databaseCleaner;
    @Autowired
    private OptionRepository optionRepository;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        databaseCleaner.executeTruncate();
    }

    @DisplayName("퀴즈를 생성할 수 있다.")
    @Test
    void should_enroll_quiz_when_requested() {
        // given
        QuizCreateRequest quizCreateRequest = QuizFixture.QUIZ.getQuizCreateRequest();

        // when & then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(quizCreateRequest)
                .when().post("/quizzes")
                .then().log().all()
                .statusCode(201);
    }

    @DisplayName("오늘의 퀴즈 목록을 조회할 수 있다")
    @Test
    void should_find_today_quiz_list() {
        // given
        QuizCreateRequest quizCreateRequest = QuizFixture.QUIZ.getQuizCreateRequest();

        // when
        Long quizId = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(quizCreateRequest)
                .when().post("/quizzes")
                .then().log().all()
                .statusCode(201)
                .extract()
                .jsonPath().getLong("quizId");

        // then
        RestAssured.given().log().all()
                .when().get("/quizzes/today")
                .then().log().all()
                .statusCode(200)
                .body("quizIds", hasItem(quizId.intValue()));
    }

    @DisplayName("ID를 통해 퀴즈를 조회할 수 있다.")
    @Test
    void should_find_quiz_by_id() {
        // given
        QuizCreateRequest quizCreateRequest = QuizFixture.QUIZ.getQuizCreateRequest();

        // when
        Long quizId = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(quizCreateRequest)
                .when().post("/quizzes")
                .then().log().all()
                .statusCode(201)
                .extract()
                .jsonPath().getLong("quizId");

        // then
        RestAssured.given().log().all()
                .when().get("/quizzes/" + quizId)
                .then().log().all()
                .statusCode(200)
                .body("title", is(quizCreateRequest.getQuiz().getTitle()));
    }

    @DisplayName("퀴즈를 풀고 제출할 수 있다.")
    @Test
    void should_submit_quiz() {
        // given
        QuizCreateRequest quizCreateRequest = QuizFixture.QUIZ.getQuizCreateRequest();

        // when
        Long quizId = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(quizCreateRequest)
                .when().post("/quizzes")
                .then().log().all()
                .statusCode(201)
                .extract()
                .jsonPath().getLong("quizId");

        Set<Long> answerOptionIds = optionRepository.findByQuizId(quizId).stream()
                .filter(Option::isAnswer)
                .map(Option::getId)
                .collect(Collectors.toSet());

        QuizSubmitRequest quizSubmitRequest = new QuizSubmitRequest(quizId, answerOptionIds);
        QuizzesSubmitRequest quizzesSubmitRequest = new QuizzesSubmitRequest(List.of(quizSubmitRequest));

        // then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(quizzesSubmitRequest)
                .when().post("/quizzes/submit")
                .then().log().all()
                .statusCode(200)
                .body("totalQuizCount", is(1), "correctCount", is(1));
    }
}
