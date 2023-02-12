package com.ssafy.trycatch.qna.domain;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AnswerRepository extends PagingAndSortingRepository<Answer, Long> {

    List<Answer> findByQuestion_Id(Long id);

    List<Answer> findByCreatedAtGreaterThanEqual(LocalDateTime startFrom);
    
}