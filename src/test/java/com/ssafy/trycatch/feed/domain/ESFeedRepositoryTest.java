package com.ssafy.trycatch.feed.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;

import java.util.List;


@SpringBootTest
@PropertySource("classpath:application.yaml")
class ESFeedRepositoryTest {

    private final ESFeedRepository esFeedRepository;

    @Autowired
    public ESFeedRepositoryTest(ESFeedRepository esFeedRepository) {
        this.esFeedRepository = esFeedRepository;
    }

    @Test
    public void testSearch() {
        List<ESFeed> feeds = esFeedRepository.findByTags(List.of("react"));
        System.out.println(feeds);
    }
}