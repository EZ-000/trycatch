package com.ssafy.trycatch.elasticsearch.domain.repository;

import com.ssafy.trycatch.elasticsearch.domain.ESQuestion;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ESQuestionRepository extends ElasticsearchRepository<ESQuestion, String> {

    List<ESQuestion> searchByContent(String content);

    List<ESQuestion> searchByTitle(String title);

    List<ESQuestion> searchByTitleOrContent(String title, String content);
}
