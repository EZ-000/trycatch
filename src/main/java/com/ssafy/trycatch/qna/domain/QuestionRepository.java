package com.ssafy.trycatch.qna.domain;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends PagingAndSortingRepository<Question, Long> {
    List<Question> findByTitleLike(String title, Pageable pageable);

}