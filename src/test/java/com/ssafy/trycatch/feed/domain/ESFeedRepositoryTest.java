package com.ssafy.trycatch.feed.domain;

import com.ssafy.trycatch.elasticsearch.domain.ESFeed;
import com.ssafy.trycatch.elasticsearch.domain.repository.ESFeedRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

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
        Page<ESFeed> feeds =  esFeedRepository.latest(new Pageable() {
            @Override
            public int getPageNumber() {
                return 0;
            }

            @Override
            public int getPageSize() {
                return 10;
            }

            @Override
            public long getOffset() {
                return 0;
            }

            @Override
            public Sort getSort() {
                return null;
            }

            @Override
            public Pageable next() {
                return null;
            }

            @Override
            public Pageable previousOrFirst() {
                return null;
            }

            @Override
            public Pageable first() {
                return null;
            }

            @Override
            public Pageable withPage(int pageNumber) {
                return null;
            }

            @Override
            public boolean hasPrevious() {
                return false;
            }
        });

//        List<ESFeed> feeds = esFeedRepository.searchByContent("react");
        System.out.println(feeds.get().count());
    }
}