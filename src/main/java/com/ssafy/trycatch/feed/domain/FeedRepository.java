package com.ssafy.trycatch.feed.domain;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface FeedRepository extends PagingAndSortingRepository<Feed, Long> {
}