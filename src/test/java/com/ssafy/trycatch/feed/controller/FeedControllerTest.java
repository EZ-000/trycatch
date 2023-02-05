package com.ssafy.trycatch.feed.controller;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
@PropertySource("classpath:application.yaml")
class FeedControllerTest {

    private MockMvc mockMvc;

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
    void findFeeds() throws Exception {
        this.mockMvc.perform(
                    get("/" + apiVersion + "/feed/search")
            )
                    .andExpect(status().isOk())
                    .andDo(document("feed"));
    }
}