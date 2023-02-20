package com.ssafy.trycatch.qna.domain;

import com.ssafy.trycatch.common.domain.QuestionCategory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends PagingAndSortingRepository<Question, Long> {
    List<Question> findByCategoryNameAndHiddenOrderByCreatedAtDesc(
            QuestionCategory categoryName, Boolean hidden, Pageable pageable);

    List<Question> findAllByOrderByLikesDesc(Pageable pageable);

    List<Question> findByCategoryNameOrderByLikesDesc(QuestionCategory categoryName, Pageable pageable);

}