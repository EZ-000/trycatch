package com.ssafy.trycatch.feed.service;

import com.ssafy.trycatch.elasticsearch.domain.ESFeed;
import com.ssafy.trycatch.elasticsearch.domain.repository.ESFeedRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FeedService {

    private final ESFeedRepository esFeedRepository;

    @Autowired
    public FeedService(
            ESFeedRepository esFeedRepository
    ) {
        this.esFeedRepository = esFeedRepository;
    }

    public Page<ESFeed> findAll(Pageable pageable) {
        return esFeedRepository.findAll(pageable);
    }

    public Page<ESFeed> commonSearch(String query, Pageable pageable) {
        log.info(query);
        return esFeedRepository.searchByTitleOrContent(query, query, pageable);
    }

    public Page<ESFeed> advanceSearch(String queryString, Pageable pageable) {
        return esFeedRepository.searchByQueryString(queryString, pageable);
    }
}
