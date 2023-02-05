package com.ssafy.trycatch.qna.domain;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends PagingAndSortingRepository<Answer, Long> {
    @Query("select a from Answer a where a.question.id = :questionId")
    List<Answer> findByQuestionId(Long questionId);

}