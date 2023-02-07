package com.ssafy.trycatch.common.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.trycatch.common.controller.dto.LikesRequestDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@PropertySource("classpath:application-local.yml")
class LikesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${apiPrefix}")
    private String apiVersion;

    @Test
    void likeTarget() throws Exception {

        final LikesRequestDto requestDto = LikesRequestDto.builder()
                .id(1L)
                .type("DEFAULT")
                .build();

        this.mockMvc.perform(post("/" + apiVersion + "/like")
                                     .contentType(MediaType.APPLICATION_JSON)
                                     .content(objectMapper.writeValueAsString(requestDto)))
                    .andExpect(status().isCreated())
                    .andDo(MockMvcRestDocumentation.document(
                            "like-target",
                            requestFields(
                                    fieldWithPath("id").description("좋아요를 누를 대상의 id"),
                                    fieldWithPath("type").description(
                                            "좋아요를 누를 대상의 타입 `QUESTION`, `ANSWER`, `FEED`, `ROADMAP`, `USER`, `DEFAULT`")),
                            requestHeaders(headerWithName("Authorization").description("JWT 토큰").optional())
                    ));
    }

    @Test
    void unlikeTarget() throws Exception {

        final LikesRequestDto requestDto = LikesRequestDto.builder()
                .id(1L)
                .type("DEFAULT")
                .build();

        this.mockMvc.perform(put("/" + apiVersion + "/like").contentType(MediaType.APPLICATION_JSON)
                                                             .content(objectMapper.writeValueAsString(requestDto)))
                    .andExpect(status().isNoContent())
                    .andDo(MockMvcRestDocumentation.document(
                            "unlike-target",
                            requestFields(
                                    fieldWithPath("id").description("좋아요를 취소할 대상의 id"),
                                    fieldWithPath("type").description(
                                            "좋아요를 취소할 대상의 타입 `QUESTION`, `ANSWER`, `FEED`, `ROADMAP`, `USER`, `DEFAULT`")),
                            requestHeaders(headerWithName("Authorization").description("JWT 토큰").optional())
                    ));
    }
}