package com.ssafy.trycatch.elasticsearch.domain.repository;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.trycatch.elasticsearch.domain.ESQuestion;

@Repository
public interface ESQuestionRepository extends ElasticsearchRepository<ESQuestion, String> {

    List<ESQuestion> searchByContent(String content);

    List<ESQuestion> searchByTitle(String title);

    List<ESQuestion> searchByTitleOrContent(String title, String content);
}
