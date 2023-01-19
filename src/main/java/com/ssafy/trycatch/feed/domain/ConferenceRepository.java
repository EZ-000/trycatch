package com.ssafy.trycatch.feed.domain;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface ConferenceRepository extends PagingAndSortingRepository<Conference, Long> {
}