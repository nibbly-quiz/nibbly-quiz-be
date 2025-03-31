package com.nibbly.quiz.presentation;

import static org.hamcrest.Matchers.hasItem;

import com.nibbly.global.supports.DatabaseCleaner;
import com.nibbly.quiz.dto.request.OptionCreateRequest;
import com.nibbly.quiz.dto.request.QuestionCreateRequest;
import com.nibbly.quiz.dto.request.QuizCreateRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.time.LocalDate;
import java.util.List;
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
        QuestionCreateRequest questionCreateRequest = new QuestionCreateRequest(
                "인덱스에 대한 설명으로 바르지 않은 것은?",
                LocalDate.now().plusDays(1)
        );

        OptionCreateRequest optionCreateRequest1 = new OptionCreateRequest("데이터베이스의 기본키로 사용할 수 있다.", false);
        OptionCreateRequest optionCreateRequest2 = new OptionCreateRequest("중복을 허용한다.", false);
        OptionCreateRequest optionCreateRequest3 = new OptionCreateRequest("NULL 값을 가질 수 있다.", false);
        OptionCreateRequest optionCreateRequest4 = new OptionCreateRequest("인덱스를 생성하면 해당 컬럼의 데이터가 정렬된다.", true);

        QuizCreateRequest request = new QuizCreateRequest(
                questionCreateRequest,
                List.of(optionCreateRequest1, optionCreateRequest2, optionCreateRequest3, optionCreateRequest4)
        );

        // when & then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when().post("/quizzes")
                .then().log().all()
                .statusCode(201);
    }

    @DisplayName("오늘의 퀴즈 목록을 조회할 수 있다")
    @Test
    void should_find_today_quiz_list() {
        // given
        QuizCreateRequest request = new QuizCreateRequest(
                new QuestionCreateRequest("오늘의 문제", LocalDate.now()),
                List.of(
                        new OptionCreateRequest("정답", true),
                        new OptionCreateRequest("오답", false)
                )
        );

        // when
        String locationHeader = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when().post("/quizzes")
                .then().log().all()
                .statusCode(201)
                .extract()
                .header("Location");
        Long quizId = Long.parseLong(locationHeader.substring(locationHeader.lastIndexOf("/") + 1));

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
        QuizCreateRequest request = new QuizCreateRequest(
                new QuestionCreateRequest("오늘의 문제", LocalDate.now()),
                List.of(
                        new OptionCreateRequest("정답", true),
                        new OptionCreateRequest("오답", false)
                )
        );

        // when
        String locationHeader = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when().post("/quizzes")
                .then().log().all()
                .statusCode(201)
                .extract()
                .header("Location");
        Long quizId = Long.parseLong(locationHeader.substring(locationHeader.lastIndexOf("/") + 1));

        // then
        RestAssured.given().log().all()
                .when().get("/quizzes/" + quizId)
                .then().log().all()
                .statusCode(200)
                .body("question.text", org.hamcrest.Matchers.equalTo("오늘의 문제"));
    }
}
