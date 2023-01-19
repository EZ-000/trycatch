package com.ssafy.trycatch.gamification.domain;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface MyBadgeRepository extends PagingAndSortingRepository<MyBadge, Long> {
}