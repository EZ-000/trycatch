package com.ssafy.trycatch.feed.service;

import com.ssafy.trycatch.elasticsearch.domain.ESFeed;
import com.ssafy.trycatch.elasticsearch.domain.repository.ESFeedRepository;
import com.ssafy.trycatch.feed.service.exception.ESFeedNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ESFeedService {

    private final ESFeedRepository esFeedRepository;

    @Autowired
    public ESFeedService(ESFeedRepository esFeedRepository) {
        this.esFeedRepository = esFeedRepository;
    }

    public ESFeed findById(String feedId) {
        return esFeedRepository.findById(feedId)
                .orElseThrow(ESFeedNotFoundException::new);
    }
}
