package com.ssafy.trycatch.elasticsearch.domain.repository;

import com.ssafy.trycatch.elasticsearch.domain.ESFeed;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ESFeedRepository extends ElasticsearchRepository<ESFeed, String> {

    @Query("{\"query_string\": {\"query\": \"?0\", \"default_field\": \"*\"}}")
    Page<ESFeed> searchByQueryString(String queryString, Pageable pageable);

    @Query("{\"multi_match\": { \"query\" : \"?0\", \"fields\": [ \"title\", \"content\" ]}}")
    Page<ESFeed> searchByTitleOrContent(String query, Pageable pageable);

    @Query("{" +
            "  \"script_score\": {" +
            "    \"query\": {" +
            "      \"multi_match\" : {" +
            "        \"query\": \"?0\"," +
            "        \"fields\": [\"title\", \"content\"]" +
            "      }" +
            "    }," +
            "    \"script\": {" +
            "      \"source\": \"cosineSimilarity(params.query_vector, doc['vector']) + 1.0\"," +
            "      \"params\": {" +
            "        \"query_vector\": ?1" +
            "      }" +
            "    }" +
            "  }" +
            "}")
    Page<ESFeed> searchByQueryAndVector(String query, List<Float> vector, Pageable pageable);

    @Query("{" +
            "  \"script_score\": {" +
            "    \"query\": {" +
            "       \"match_all\": {}" +
            "    }," +
            "    \"script\": {" +
            "      \"source\": \"cosineSimilarity(params.query_vector, doc['vector']) + 1.0\"," +
            "      \"params\": {" +
            "        \"query_vector\": ?0" +
            "      }" +
            "    }" +
            "  }" +
            "}")
    Page<ESFeed> searchByVector(List<Float> vector, Pageable pageable);
}





