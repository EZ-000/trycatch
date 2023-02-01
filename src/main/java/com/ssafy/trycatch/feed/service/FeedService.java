package com.ssafy.trycatch.feed.service;

import com.ssafy.trycatch.elasticsearch.domain.ESFeed;
import com.ssafy.trycatch.elasticsearch.domain.repository.ESFeedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedService {

    private final ESFeedRepository feedRepository;

    public Page<ESFeed> latestFeeds(Pageable pageable) {
        return feedRepository.latest(pageable);
    }

    public List<ESFeed> searchByContent(String content) {
        return feedRepository.searchByContent(content);
    }

    @Autowired
    public FeedService(ESFeedRepository feedRepository) {
        this.feedRepository = feedRepository;
    }
}
