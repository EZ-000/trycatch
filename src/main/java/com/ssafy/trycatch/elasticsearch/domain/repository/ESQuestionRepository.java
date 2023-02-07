package com.ssafy.trycatch.elasticsearch.domain.repository;

import com.ssafy.trycatch.elasticsearch.domain.ESQuestion;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ESQuestionRepository extends ElasticsearchRepository<ESQuestion, String> {

    @Query("{\"multi_match\": {\"query\": \"?0\", \"fields\": [\"title\", \"content\"]}}")
    Page<ESQuestion> searchByTitleOrContent(String query, Pageable pageable);
}
