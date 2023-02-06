package com.ssafy.trycatch.qna.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@ExtendWith(RestDocumentationExtension.class)
@PropertySource("classpath:application-local.yml")
class QuestionControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${apiPrefix}")
    private String apiVersion;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext,
               RestDocumentationContextProvider documentationContextProvider) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                                      .apply(MockMvcRestDocumentation.documentationConfiguration(
                                              documentationContextProvider))
                                      .build();
    }

    @Test
    void findAllQuestions() {
    }

    @Test
    void createQuestion() {
    }

    @Test
    void putQuestion() {
    }

    @Test
    void deleteQuestion() {
    }

    @Test
    void findQuestionById() {
    }

    @Test
    void createAnswers() {
    }

    @Test
    void putAnswer() {
    }

    @Test
    void search() {
    }

    @Test
    void acceptAnswer() {
    }

    @Test
    void suggestQuestions() {
    }

//    @Test
//    @Order(1)
//    @DisplayName("CreateQuestionRequestDto 를 받아 데이터베이스에 저장")
//    void createQuestion() throws Exception {
//
//        final String newQuestion = "{" +
//                "\"authorId\": 1," +
//                "\"categoryId\": 1," +
//                "\"title\": \"test title\"," +
//                "\"content\": \"{}\"," +
//                "\"hidden\": false" +
//                '}';
//
//        this.mockMvc.perform(
//                        post("/" + apiVersion + "/question")
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .content(newQuestion)
//                                .accept(MediaType.APPLICATION_JSON)
//                )
//                .andExpect(status().isOk())
//                .andDo(document("index"));
//    }

//    @Test
//    @Order(2)
//    @DisplayName("Question id로 Question 을 찾아 DTO 로 변환 후 반환")
//    void findQuestionById() throws Exception {
//        this.mockMvc.perform(get("/" + apiVersion + "/question/1")
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andDo(document("index"));
//    }

//    @Test
//    @Order(3)
//    @DisplayName("모든 Question 리스트를 조회")
//    void findAllQuestions() throws Exception {
//        this.mockMvc.perform(get("/" + apiVersion + "/question")
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andDo(document("index"))
//                .andDo(print());
//    }
}