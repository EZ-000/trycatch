package com.ssafy.trycatch.feed.service;

import com.ssafy.trycatch.common.service.CrudService;
import com.ssafy.trycatch.elasticsearch.domain.ESFeed;
import com.ssafy.trycatch.elasticsearch.domain.repository.ESFeedRepository;
import com.ssafy.trycatch.feed.domain.Feed;
import com.ssafy.trycatch.feed.domain.FeedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedService extends CrudService<Feed, Long, FeedRepository> {

    private final ESFeedRepository esFeedRepository;

    public Page<ESFeed> latestFeeds(Pageable pageable) {
        return esFeedRepository.latest(pageable);
    }

    public List<ESFeed> searchByContent(String content) {
        return esFeedRepository.searchByContent(content);
    }

    @Autowired
    public FeedService(
            FeedRepository feedRepository,
            ESFeedRepository esFeedRepository
    ) {
        super(feedRepository);
        this.esFeedRepository = esFeedRepository;
    }
}
