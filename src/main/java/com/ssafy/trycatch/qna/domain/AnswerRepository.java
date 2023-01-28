package com.ssafy.trycatch.qna.domain;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface AnswerRepository extends PagingAndSortingRepository<Answer, Long> {
}