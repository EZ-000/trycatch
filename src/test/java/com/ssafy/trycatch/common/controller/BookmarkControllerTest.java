package com.ssafy.trycatch.common.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.trycatch.common.controller.dto.BookmarkRequestDto;
import com.ssafy.trycatch.common.domain.TargetType;
import com.ssafy.trycatch.common.service.BookmarkService;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@SpringBootTest
//@ExtendWith(RestDocumentationExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@PropertySource("classpath:application-local.yml")
class BookmarkControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${apiPrefix}")
    private String apiVersion;

    @Test
    void bookmarkTarget() throws Exception {

        final BookmarkRequestDto requestDto = BookmarkRequestDto.builder()
                .id(1L)
                .type("DEFAULT")
                .build();

        this.mockMvc.perform(
                        post("/" + apiVersion + "/bookmark")
                                .content(objectMapper.writeValueAsString(requestDto))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(document("bookmark-add",
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("인증 토큰").optional()
                        )
                ));
    }

    @Test
    void removeBookmark() throws Exception {
        final BookmarkRequestDto requestDto = BookmarkRequestDto.builder()
                .id(1L)
                .type("DEFAULT")
                .build();

        this.mockMvc.perform(
                        put("/" + apiVersion + "/bookmark")
                                .content(objectMapper.writeValueAsString(requestDto))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(document("bookmark-remove",
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("인증 토큰").optional()
                        ),
                        requestFields(
                                fieldWithPath("id").description("북마크할 대상의 PK 아이디").type("number"),
                                fieldWithPath("type").description("북마크할 대상 타입 `QUESTION`, `ANSWER`, `FEED`, `ROADMAP`, `USER`, `DEFAULT`").type("string")
                        )
                ));

    }

    @Test
    void findBookmarkedQuestions() throws Exception {

        this.mockMvc.perform(
                        get("/" + apiVersion + "/bookmark/question")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(document("bookmark-find",
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("인증 토큰").optional()
                        ),
                        responseFields(
                                fieldWithPath("questionId"),
                                fieldWithPath("title"),
                                fieldWithPath("content"),
                                fieldWithPath("tags"),
                                fieldWithPath("viewCount"),
                                fieldWithPath("likeCount"),
                                fieldWithPath("answerCount"),
                                fieldWithPath("createAt")
                        )
                ));

    }

    @Test
    void findBookmarkedRoadmaps() throws Exception {

        this.mockMvc.perform(
                        get("/" + apiVersion + "/bookmark/roadmap")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(document("bookmark-find",
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("인증 토큰").optional()
                        )
                ));
    }
}