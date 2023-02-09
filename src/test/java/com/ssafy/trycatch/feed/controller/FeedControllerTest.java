package com.ssafy.trycatch.feed.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@PropertySource("classpath:application-local.yml")
class FeedControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Value("${apiPrefix}")
    private String apiVersion;

//     @Test
//     void findFeeds() throws Exception {

//         this.mockMvc.perform(
//                 get("/" + apiVersion + "/feed/search")
//                         .queryParam("page", "0")
//                         .queryParam("size", "2")
//                         .queryParam("sort", "date")
//                         .queryParam("subscribe", "false")
//                         .queryParam("advanced", "true")
//                         .queryParam("publishDateStart", LocalDate.of(2000, 1, 1).toString())
//                         .queryParam("publishDateEnd", LocalDate.now().toString())
//                         .queryParam("query", "( _id : 4GLbL4YB5tqxoIWWuVAu ) OR ( _id : 6GLbL4YB5tqxoIWWuVD2 )")
//             )
//                     .andExpect(status().isOk())
//                     .andDo(document(
//                             "feed",
//                             requestHeaders(headerWithName(HttpHeaders.AUTHORIZATION).description("인증 토큰")
//                                                                                     .optional()),
//                             requestParameters(
//                                     parameterWithName("query").description("검색어"),
//                                     parameterWithName("page").description("페이지"),
//                                     parameterWithName("size").description("페이지 사이즈"),
//                                     parameterWithName("sort").description("정렬"),
//                                     parameterWithName("subscribe").description("구독자 피드"),
//                                     parameterWithName("advanced").description("고급 검색"),
//                                     parameterWithName("publishDateStart").description("게시일 시작"),
//                                     parameterWithName("publishDateEnd").description("게시일 끝")
//                             ),
//                             responseFields(
//                                     fieldWithPath("feedList.[].id").description("피드 RDB 아이디"),
//                                     fieldWithPath("feedList.[].feedId").description("피드 아이디"),
//                                     fieldWithPath("feedList.[].title").description("피드 제목"),
//                                     fieldWithPath("feedList.[].summary").description("피드 요약"),
//                                     fieldWithPath("feedList.[].companyName").description("피드 회사"),
//                                     fieldWithPath("feedList.[].logoSrc").description("피드 로고"),
//                                     fieldWithPath("feedList.[].createAt").description("피드 생성일"),
//                                     fieldWithPath("feedList.[].url").description("피드 링크"),
//                                     fieldWithPath("feedList.[].tags").description("피드 태그").optional(),
//                                     fieldWithPath("feedList.[].keywords").description("피드 키워드").optional(),
//                                     fieldWithPath("feedList.[].isBookmarked").description("북마크 여부"),
//                                     fieldWithPath("feedList.[].thumbnailImage").description("썸네일 이미지")
//                             )
//                     ));
//     }
}
