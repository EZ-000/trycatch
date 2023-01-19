package com.ssafy.trycatch.user.domain;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface SubscriptionRepository extends PagingAndSortingRepository<Subscription, Long> {
}