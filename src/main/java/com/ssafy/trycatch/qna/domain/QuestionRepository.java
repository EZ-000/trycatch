package com.ssafy.trycatch.qna.domain;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.trycatch.common.domain.QuestionCategory;

@Repository
public interface QuestionRepository extends PagingAndSortingRepository<Question, Long> {
    List<Question> findByTitleLike(String title, Pageable pageable);

    List<Question> findByCategoryNameOrderByCreatedAtDesc(QuestionCategory categoryName, Pageable pageable);

}