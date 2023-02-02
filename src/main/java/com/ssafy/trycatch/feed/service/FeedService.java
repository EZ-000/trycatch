package com.ssafy.trycatch.feed.service;

import com.ssafy.trycatch.elasticsearch.domain.ESFeed;
import com.ssafy.trycatch.elasticsearch.domain.repository.ESFeedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class FeedService {

    private final ESFeedRepository esFeedRepository;

    public Page<ESFeed> commonSearch(String query, Pageable pageable) {
        return esFeedRepository.searchByTitleOrContent(query, query, pageable);
    }

    public Page<ESFeed> advanceSearch(String queryString, Pageable pageable) {
        return esFeedRepository.searchByQueryString(queryString, pageable);
    }


    @Autowired
    public FeedService(
            ESFeedRepository esFeedRepository
    ) {
        this.esFeedRepository = esFeedRepository;
    }
}
