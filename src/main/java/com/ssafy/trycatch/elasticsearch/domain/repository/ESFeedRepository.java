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

    // True True True True
    @Query("{\n" +
            "    \"bool\": {\n" +
            "        \"must\": {\n" +
            "            \"script_score\": {\n" +
            "                \"query\": {\n" +
            "                    \"query_string\": {\n" +
            "                        \"query\": \"?0\",\n" +
            "                        \"default_field\": \"*\"\n" +
            "                    }\n" +
            "                },\n" +
            "                \"script\": {\n" +
            "                    \"source\": \"cosineSimilarity(params.query_vector, doc['vector']) + 1.0\",\n" +
            "                    \"params\": {\n" +
            "                        \"query_vector\": ?1\n" +
            "                    }\n" +
            "                }\n" +
            "            }\n" +
            "        },\n" +
            "        \"filter\": {\n" +
            "            \"terms\": {\n" +
            "                \"pk\": ?2\n" +
            "            }\n" +
            "        }\n" +
            "    }\n" +
            "}")
    Page<ESFeed> TrueQueryTrueVectorTrueSubscribeTrueAdvanced(
            String query, List<Float> vector, List<Long> subscribe, Pageable pageable);

    // True True True False
    @Query("{\n" +
            "    \"bool\": {\n" +
            "        \"must\": {\n" +
            "            \"script_score\": {\n" +
            "                \"query\": {\n" +
            "                    \"multi_match\": {\n" +
            "                        \"query\": \"?0\",\n" +
            "                        \"fields\": [\"title\", \"content\"]\n" +
            "                    }\n" +
            "                },\n" +
            "                \"script\": {\n" +
            "                    \"source\": \"cosineSimilarity(params.query_vector, doc['vector']) + 1.0\",\n" +
            "                    \"params\": {\n" +
            "                        \"query_vector\": ?1\n" +
            "                    }\n" +
            "                }\n" +
            "            }\n" +
            "        },\n" +
            "        \"filter\": {\n" +
            "            \"terms\": {\n" +
            "                \"pk\": ?2\n" +
            "            }\n" +
            "        }\n" +
            "    }\n" +
            "}")
    Page<ESFeed> TrueQueryTrueVectorTrueSubscribeFalseAdvanced(
            String query, List<Float> vector, List<Long> subscribe, Pageable pageable);

    // True True False T
    @Query("{\n" +
            "    \"script_score\": {\n" +
            "        \"query\": {\n" +
            "            \"query_string\": {\n" +
            "                \"query\": \"?0\",\n" +
            "                \"default_field\": \"*\"\n" +
            "            }\n" +
            "        },\n" +
            "        \"script\": {\n" +
            "            \"source\": \"cosineSimilarity(params.query_vector, doc['vector']) + 1.0\",\n" +
            "            \"params\": {\n" +
            "                \"query_vector\": ?1\n" +
            "            }\n" +
            "        }\n" +
            "    }\n" +
            "}")
    Page<ESFeed> TrueQueryTrueVectorFalseSubscribeTrueAdvanced(
            String query, List<Float> vector, Pageable pageable);

    // True True False F
    @Query("{\n" +
            "    \"script_score\": {\n" +
            "        \"query\": {\n" +
            "            \"multi_match\": {\n" +
            "                \"query\": \"?0\",\n" +
            "                \"fields\": [\"title\", \"content\"]\n" +
            "            }\n" +
            "        },\n" +
            "        \"script\": {\n" +
            "            \"source\": \"cosineSimilarity(params.query_vector, doc['vector']) + 1.0\",\n" +
            "            \"params\": {\n" +
            "                \"query_vector\": ?1\n" +
            "            }\n" +
            "        }\n" +
            "    }\n" +
            "}")
    Page<ESFeed> TrueQueryTrueVectorFalseSubscribeFalseAdvanced(
            String query, List<Float> vector, Pageable pageable);

    // True False True T
    @Query("{\n" +
            "    \"bool\": {\n" +
            "        \"must\": {\n" +
            "            \"query\": {\n" +
            "                \"query_string\": {\n" +
            "                    \"query\": \"?0\",\n" +
            "                    \"default_field\": \"*\"\n" +
            "                }\n" +
            "            }\n" +
            "        },\n" +
            "        \"filter\": {\n" +
            "            \"terms\": {\n" +
            "                \"pk\": ?1\n" +
            "            }\n" +
            "        }\n" +
            "    }\n" +
            "}")
    Page<ESFeed> TrueQueryFalseVectorTrueSubscribeTrueAdvanced(
            String query, List<Long> subscribe, Pageable pageable);

    // True False True F
    @Query("{\n" +
            "    \"bool\": {\n" +
            "        \"must\": {\n" +
            "            \"query\": {\n" +
            "                \"multi_match\": {\n" +
            "                    \"query\": \"?0\",\n" +
            "                    \"fields\": [\"title\", \"content\"]\n" +
            "                }\n" +
            "            }\n" +
            "        },\n" +
            "        \"filter\": {\n" +
            "            \"terms\": {\n" +
            "                \"pk\": ?1\n" +
            "            }\n" +
            "        }\n" +
            "    }\n" +
            "}")
    Page<ESFeed> TrueQueryFalseVectorTrueSubscribeFalseAdvanced(
            String query, List<Long> subscribe, Pageable pageable);

    // True False False T
    @Query("{\n" +
            "    \"query_string\": {\n" +
            "        \"query\": \"?0\",\n" +
            "        \"default_field\": \"*\"\n" +
            "    }\n" +
            "}")
    Page<ESFeed> TrueQueryFalseVectorFalseSubscribeTrueAdvanced(
            String query, Pageable pageable);

    // True False False F
    @Query("{\n" +
            "    \"multi_match\": {\n" +
            "        \"query\": \"?0\",\n" +
            "        \"fields\": [\"title\", \"content\"]\n" +
            "    }\n" +
            "}")
    Page<ESFeed> TrueQueryFalseVectorFalseSubscribeFalseAdvanced(
            String query, Pageable pageable);

    // False True True T
    @Query("{\n" +
            "    \"bool\": {\n" +
            "        \"must\": {\n" +
            "            \"script_score\": {\n" +
            "                \"query\": {\n" +
            "                    \"match_all\": {}\n" +
            "                },\n" +
            "                \"script\": {\n" +
            "                    \"source\": \"cosineSimilarity(params.query_vector, doc['vector']) + 1.0\",\n" +
            "                    \"params\": {\n" +
            "                        \"query_vector\": ?0\n" +
            "                    }\n" +
            "                }\n" +
            "            }\n" +
            "        },\n" +
            "        \"filter\": {\n" +
            "            \"terms\": {\n" +
            "                \"pk\": ?1\n" +
            "            }\n" +
            "        }\n" +
            "    }\n" +
            "}")
    Page<ESFeed> FalseQueryTrueVectorTrueSubscribeTrueAdvanced(
            List<Float> vector, List<Long> subscribe, Pageable pageable);

    // False True True F
    @Query("{\n" +
            "    \"bool\": {\n" +
            "        \"must\": {\n" +
            "            \"script_score\": {\n" +
            "                \"query\": {\n" +
            "                    \"match_all\": {}\n" +
            "                },\n" +
            "                \"script\": {\n" +
            "                    \"source\": \"cosineSimilarity(params.query_vector, doc['vector']) + 1.0\",\n" +
            "                    \"params\": {\n" +
            "                        \"query_vector\": ?0\n" +
            "                    }\n" +
            "                }\n" +
            "            }\n" +
            "        },\n" +
            "        \"filter\": {\n" +
            "            \"terms\": {\n" +
            "                \"pk\": ?1\n" +
            "            }\n" +
            "        }\n" +
            "    }\n" +
            "}")
    Page<ESFeed> FalseQueryTrueVectorTrueSubscribeFalseAdvanced(
            List<Float> vector, List<Long> subscribe, Pageable pageable);

    // False True False T
    @Query("{\n" +
            "    \"script_score\": {\n" +
            "        \"query\": {\n" +
            "            \"match_all\": {}\n" +
            "        },\n" +
            "        \"script\": {\n" +
            "            \"source\": \"cosineSimilarity(params.query_vector, doc['vector']) + 1.0\",\n" +
            "            \"params\": {\n" +
            "                \"query_vector\": ?0\n" +
            "            }\n" +
            "        }\n" +
            "    }\n" +
            "}")
    Page<ESFeed> FalseQueryTrueVectorFalseSubscribeTrueAdvanced(
            List<Float> vector, Pageable pageable);

    // False True False F
    @Query("{\n" +
            "    \"script_score\": {\n" +
            "        \"query\": {\n" +
            "            \"match_all\": {}\n" +
            "        },\n" +
            "        \"script\": {\n" +
            "            \"source\": \"cosineSimilarity(params.query_vector, doc['vector']) + 1.0\",\n" +
            "            \"params\": {\n" +
            "                \"query_vector\": ?0\n" +
            "            }\n" +
            "        }\n" +
            "    }\n" +
            "}")
    Page<ESFeed> FalseQueryTrueVectorFalseSubscribeFalseAdvanced(
            List<Float> vector, Pageable pageable);

    // False False True T
    @Query("{\n" +
            "    \"bool\": {\n" +
            "        \"must\": {\n" +
            "            \"query\": {\n" +
            "                \"match_all\": {}\n" +
            "            }\n" +
            "        },\n" +
            "        \"filter\": {\n" +
            "            \"terms\": {\n" +
            "                \"pk\": ?0\n" +
            "            }\n" +
            "        }\n" +
            "    }\n" +
            "}")
    Page<ESFeed> FalseQueryFalseVectorTrueSubscribeTrueAdvanced(
            List<Long> subscribe, Pageable pageable);

    // False False True F
    @Query("{\n" +
            "    \"bool\": {\n" +
            "        \"must\": {\n" +
            "            \"query\": {\n" +
            "                \"match_all\": {}\n" +
            "            }\n" +
            "        },\n" +
            "        \"filter\": {\n" +
            "            \"terms\": {\n" +
            "                \"pk\": ?0\n" +
            "            }\n" +
            "        }\n" +
            "    }\n" +
            "}")
    Page<ESFeed> FalseQueryFalseVectorTrueSubscribeFalseAdvanced(
            List<Long> subscribe, Pageable pageable);

    // False False False T
    @Query("{\n" +
            "    \"match_all\": {}\n" +
            "}")
    Page<ESFeed> FalseQueryFalseVectorFalseSubscribeTrueAdvanced(
            Pageable pageable);

    // False False False F
    @Query("{\n" +
            "    \"match_all\": {}\n" +
            "}")
    Page<ESFeed> FalseQueryFalseVectorFalseSubscribeFalseAdvanced(
            Pageable pageable);
}





