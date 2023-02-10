package com.ssafy.trycatch.elasticsearch.domain.repository;

import com.ssafy.trycatch.elasticsearch.domain.ESQuestion;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ESQuestionRepository extends ElasticsearchRepository<ESQuestion, String> {

    @Query("{" +
            "    \"bool\": {" +
            "        \"must\": [" +
            "            {" +
            "                \"multi_match\": {" +
            "                    \"query\": \"?0\"," +
            "                    \"fields\": [\"title\", \"content\"]" +
            "                }" +
            "            }," +
            "            {" +
            "                \"match\": {" +
            "                    \"category\": \"?1\"" +
            "                }" +
            "            }" +
            "        ]" +
            "    }" +
            "}")
    Page<ESQuestion> searchByTitleOrContentAndCategory(String query, String category, Pageable pageable);
}
