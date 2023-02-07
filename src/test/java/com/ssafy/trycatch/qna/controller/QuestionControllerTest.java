package com.ssafy.trycatch.qna.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.function.Supplier;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@PropertySource("classpath:application-local.yml")
class QuestionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${apiPrefix}")
    private String apiVersion;

    private final Supplier<String> createPath = () -> "/" + apiVersion;

    @Test
    void findAllQuestions() throws Exception {

        this.mockMvc.perform(
                get(createPath.get())
        ).andDo(document("question-find-all"));
    }

    @Test
    void createQuestion() throws Exception {

        this.mockMvc.perform(
                post(createPath.get())
        ).andDo(document("question-create"));
    }

    @Test
    void putQuestion() throws Exception {

        this.mockMvc.perform(
                put(createPath.get() + "/{id}", 1)
        ).andDo(document("question-edit"));
    }

    @Test
    void deleteQuestion() throws Exception {

        this.mockMvc.perform(
                delete(createPath.get() + "/{id}", 1)
        ).andDo(document("question-delete"));
    }

    @Test
    void findQuestionById() throws Exception {

        this.mockMvc.perform(
                get(createPath.get() + "/{id}", 1)
        ).andDo(document("question-detail"));
    }

    @Test
    void createAnswers() throws Exception {

        this.mockMvc.perform(
                post(createPath.get() + "/{id}/answer", 1)
        ).andDo(document("answer-create"));
    }

    @Test
    void putAnswer() throws Exception {
        this.mockMvc.perform(
                put(createPath.get() + "/{id}/answer", 1)
        ).andDo(document("answer-edit"));
    }

    @Test
    void search() throws Exception {

        this.mockMvc.perform(
                get(createPath.get() + "/search")
        ).andDo(document("question-search"));
    }

    @Test
    void acceptAnswer() throws Exception {

        this.mockMvc.perform(
                post(createPath.get() + "/{questionId}/{answerId}", 1, 1)
        ).andDo(document("answer-accept"));
    }

    @Test
    void suggestQuestions() throws Exception {

        this.mockMvc.perform(
                get(createPath.get() + "/ec")
        ).andDo(document("question-recommend"));
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