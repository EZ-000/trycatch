package com.ssafy.trycatch.user.domain;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface HistoryRepository extends PagingAndSortingRepository<History, Long> {
}