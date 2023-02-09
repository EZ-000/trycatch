package com.ssafy.trycatch.common.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.trycatch.common.controller.dto.BookmarkRequestDto;
import com.ssafy.trycatch.common.infra.config.jwt.Token;
import com.ssafy.trycatch.common.infra.config.jwt.TokenService;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@PropertySource("classpath:application-local.yml")
class BookmarkControllerTest {

    private final MockMvc mockMvc;

    private final ObjectMapper objectMapper;

    @Value("${apiPrefix}")
    private String apiVersion;

    private final Token token;

    @Autowired
    public BookmarkControllerTest(
            MockMvc mockMvc,
            ObjectMapper objectMapper,
            TokenService tokenService,
            @Value("${apiPrefix}") String apiVersion
    ) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
        this.apiVersion = apiVersion;
        this.token = tokenService.generateToken("1", "USER");
    }

    @Test
    void bookmarkTarget() throws Exception {

        final BookmarkRequestDto requestDto = BookmarkRequestDto.builder()
                .id(1L)
                .type("DEFAULT")
                .build();

        this.mockMvc.perform(
                        post("/" + apiVersion + "/bookmark")
                                .header(HttpHeaders.AUTHORIZATION, token.getToken())
                                .content(objectMapper.writeValueAsString(requestDto))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
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
                                .header(HttpHeaders.AUTHORIZATION, token.getToken())
                                .content(objectMapper.writeValueAsString(requestDto))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNoContent())
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
                                .header(HttpHeaders.AUTHORIZATION, token.getToken())
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(document("bookmark-question",
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("인증 토큰").optional()
                        ),
                        responseFields(
                                fieldWithPath("[].questionId").description("질문 고유 아이디").type("number").optional(),
                                fieldWithPath("[].title").description("질문 타이틀").type("string").optional(),
                                fieldWithPath("[].content").description("질문 내용").type("string").optional(),
                                fieldWithPath("[].tags").description("질문 태그 리스트").type("string[]").optional(),
                                fieldWithPath("[].viewCount").description("조회수").type("number").optional(),
                                fieldWithPath("[].likeCount").description("좋아요 수").type("number").optional(),
                                fieldWithPath("[].answerCount").description("답변 수").type("number").optional(),
                                fieldWithPath("[].createAt").description("timestamp milliseconds").type("number").optional()
                        )
                ));

    }

    @Test
    void findBookmarkedRoadmaps() throws Exception {

        this.mockMvc.perform(
                        get("/" + apiVersion + "/bookmark/roadmap")
                                .header(HttpHeaders.AUTHORIZATION, token.getToken())
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(document("bookmark-roadmap",
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("인증 토큰").optional()
                        ),
                        responseFields(
                                fieldWithPath("[].roadmapId").description("로드맵 아이디").type("Long").optional(),
                                fieldWithPath("[].author").description("작성자").type("SimpleUserInfo").optional(),
                                fieldWithPath("[].author.userId").description("작성자 아이디").type("SimpleUserInfo").optional(),
                                fieldWithPath("[].author.userName").description("작성자 닉네임").type("SimpleUserInfo").optional(),
                                fieldWithPath("[].author.profileImage").description("작성자 프로필 이미지").type("SimpleUserInfo").optional(),
                                fieldWithPath("[].author.companyName").description("작성자 인증 회사 이름").type("SimpleUserInfo").optional(),
                                fieldWithPath("[].author.isFollowed").description("요청자의 작성자 팔로우 여부").type("SimpleUserInfo").optional(),
                                fieldWithPath("[].title").description("로드맵 타이틀").type("String").optional(),
                                fieldWithPath("[].tag").description("로드맵 태그").type("String").optional(),
                                fieldWithPath("[].nodes").description("로드맵 노드 객체").type("String").optional(),
                                fieldWithPath("[].edges").description("로드맵 엣지 객체").type("String").optional(),
                                fieldWithPath("[].likeCount").description("좋아요 수").type("Integer").optional(),
                                fieldWithPath("[].createdAt").description("생성일, timestamp millisecond").type("Long").optional(),
                                fieldWithPath("[].updatedAt").description("업데이트 일, timestamp millisecond").type("Long").optional()
                        )
                ));
    }
}