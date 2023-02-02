package com.ssafy.trycatch.elasticsearch.domain.repository;

import com.ssafy.trycatch.elasticsearch.domain.ESFeed;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ESFeedRepository extends ElasticsearchRepository<ESFeed, String> {

    @Query("{" +
    "\"query\": {" +
        "\"query_string\": {" +
            "\"query\": \"?0\"," +
            "\"default_field\": \"*\"" +
    "}}}")
    Page<ESFeed> searchByQueryString(String queryString, Pageable pageable);

    Page<ESFeed> searchByTitleOrContent(String title, String content, Pageable pageable);
}





