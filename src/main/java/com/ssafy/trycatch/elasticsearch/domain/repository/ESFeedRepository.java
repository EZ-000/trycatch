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
    @Query("{" +
            "    \"bool\": {" +
            "        \"must\": {" +
            "            \"script_score\": {" +
            "                \"query\": {" +
            "                    \"query_string\": {" +
            "                        \"query\": \"?0\"," +
            "                        \"default_field\": \"*\"" +
            "                    }" +
            "                }," +
            "                \"script\": {" +
            "                    \"source\": \"cosineSimilarity(params.query_vector, doc['vector']) + 1.0\"," +
            "                    \"params\": {" +
            "                        \"query_vector\": ?1" +
            "                    }" +
            "                }" +
            "            }" +
            "        }," +
            "        \"filter\": {" +
            "            \"terms\": {" +
            "                \"pk\": ?2" +
            "            }" +
            "        }" +
            "    }" +
            "}")
    Page<ESFeed> TrueQueryTrueVectorTrueSubscribeTrueAdvanced(
            String query, List<Float> vector, List<Long> subscribe, Pageable pageable);

    // True True True False
    @Query("{" +
            "    \"bool\": {" +
            "        \"must\": {" +
            "            \"script_score\": {" +
            "                \"query\": {" +
            "                    \"multi_match\": {" +
            "                        \"query\": \"?0\"," +
            "                        \"fields\": [\"title\", \"content\"]" +
            "                    }" +
            "                }," +
            "                \"script\": {" +
            "                    \"source\": \"cosineSimilarity(params.query_vector, doc['vector']) + 1.0\"," +
            "                    \"params\": {" +
            "                        \"query_vector\": ?1" +
            "                    }" +
            "                }" +
            "            }" +
            "        }," +
            "        \"filter\": {" +
            "            \"terms\": {" +
            "                \"pk\": ?2" +
            "            }" +
            "        }" +
            "    }" +
            "}")
    Page<ESFeed> TrueQueryTrueVectorTrueSubscribeFalseAdvanced(
            String query, List<Float> vector, List<Long> subscribe, Pageable pageable);

    // True True False T
    @Query("{" +
            "    \"script_score\": {" +
            "        \"query\": {" +
            "            \"query_string\": {" +
            "                \"query\": \"?0\"," +
            "                \"default_field\": \"*\"" +
            "            }" +
            "        }," +
            "        \"script\": {" +
            "            \"source\": \"cosineSimilarity(params.query_vector, doc['vector']) + 1.0\"," +
            "            \"params\": {" +
            "                \"query_vector\": ?1" +
            "            }" +
            "        }" +
            "    }" +
            "}")
    Page<ESFeed> TrueQueryTrueVectorFalseSubscribeTrueAdvanced(
            String query, List<Float> vector, Pageable pageable);

    // True True False F
    @Query("{" +
            "    \"script_score\": {" +
            "        \"query\": {" +
            "            \"multi_match\": {" +
            "                \"query\": \"?0\"," +
            "                \"fields\": [\"title\", \"content\"]" +
            "            }" +
            "        }," +
            "        \"script\": {" +
            "            \"source\": \"cosineSimilarity(params.query_vector, doc['vector']) + 1.0\"," +
            "            \"params\": {" +
            "                \"query_vector\": ?1" +
            "            }" +
            "        }" +
            "    }" +
            "}")
    Page<ESFeed> TrueQueryTrueVectorFalseSubscribeFalseAdvanced(
            String query, List<Float> vector, Pageable pageable);

    // True False True T
    @Query("{" +
            "    \"bool\": {" +
            "        \"must\": {" +
            "            \"query\": {" +
            "                \"query_string\": {" +
            "                    \"query\": \"?0\"," +
            "                    \"default_field\": \"*\"" +
            "                }" +
            "            }" +
            "        }," +
            "        \"filter\": {" +
            "            \"terms\": {" +
            "                \"pk\": ?1" +
            "            }" +
            "        }" +
            "    }" +
            "}")
    Page<ESFeed> TrueQueryFalseVectorTrueSubscribeTrueAdvanced(
            String query, List<Long> subscribe, Pageable pageable);

    // True False True F
    @Query("{" +
            "    \"bool\": {" +
            "        \"must\": {" +
            "            \"query\": {" +
            "                \"multi_match\": {" +
            "                    \"query\": \"?0\"," +
            "                    \"fields\": [\"title\", \"content\"]" +
            "                }" +
            "            }" +
            "        }," +
            "        \"filter\": {" +
            "            \"terms\": {" +
            "                \"pk\": ?1" +
            "            }" +
            "        }" +
            "    }" +
            "}")
    Page<ESFeed> TrueQueryFalseVectorTrueSubscribeFalseAdvanced(
            String query, List<Long> subscribe, Pageable pageable);

    // True False False T
    @Query("{" +
            "    \"query_string\": {" +
            "        \"query\": \"?0\"," +
            "        \"default_field\": \"*\"" +
            "    }" +
            "}")
    Page<ESFeed> TrueQueryFalseVectorFalseSubscribeTrueAdvanced(
            String query, Pageable pageable);

    // True False False F
    @Query("{" +
            "    \"multi_match\": {" +
            "        \"query\": \"?0\"," +
            "        \"fields\": [\"title\", \"content\"]" +
            "    }" +
            "}")
    Page<ESFeed> TrueQueryFalseVectorFalseSubscribeFalseAdvanced(
            String query, Pageable pageable);

    // False True True T
    @Query("{" +
            "    \"bool\": {" +
            "        \"must\": {" +
            "            \"script_score\": {" +
            "                \"query\": {" +
            "                    \"match_all\": {}" +
            "                }," +
            "                \"script\": {" +
            "                    \"source\": \"cosineSimilarity(params.query_vector, doc['vector']) + 1.0\"," +
            "                    \"params\": {" +
            "                        \"query_vector\": ?0" +
            "                    }" +
            "                }" +
            "            }" +
            "        }," +
            "        \"filter\": {" +
            "            \"terms\": {" +
            "                \"pk\": ?1" +
            "            }" +
            "        }" +
            "    }" +
            "}")
    Page<ESFeed> FalseQueryTrueVectorTrueSubscribeTrueAdvanced(
            List<Float> vector, List<Long> subscribe, Pageable pageable);

    // False True True F
    @Query("{" +
            "    \"bool\": {" +
            "        \"must\": {" +
            "            \"script_score\": {" +
            "                \"query\": {" +
            "                    \"match_all\": {}" +
            "                }," +
            "                \"script\": {" +
            "                    \"source\": \"cosineSimilarity(params.query_vector, doc['vector']) + 1.0\"," +
            "                    \"params\": {" +
            "                        \"query_vector\": ?0" +
            "                    }" +
            "                }" +
            "            }" +
            "        }," +
            "        \"filter\": {" +
            "            \"terms\": {" +
            "                \"pk\": ?1" +
            "            }" +
            "        }" +
            "    }" +
            "}")
    Page<ESFeed> FalseQueryTrueVectorTrueSubscribeFalseAdvanced(
            List<Float> vector, List<Long> subscribe, Pageable pageable);

    // False True False T
    @Query("{" +
            "    \"script_score\": {" +
            "        \"query\": {" +
            "            \"match_all\": {}" +
            "        }," +
            "        \"script\": {" +
            "            \"source\": \"cosineSimilarity(params.query_vector, doc['vector']) + 1.0\"," +
            "            \"params\": {" +
            "                \"query_vector\": ?0" +
            "            }" +
            "        }" +
            "    }" +
            "}")
    Page<ESFeed> FalseQueryTrueVectorFalseSubscribeTrueAdvanced(
            List<Float> vector, Pageable pageable);

    // False True False F
    @Query("{" +
            "    \"script_score\": {" +
            "        \"query\": {" +
            "            \"match_all\": {}" +
            "        }," +
            "        \"script\": {" +
            "            \"source\": \"cosineSimilarity(params.query_vector, doc['vector']) + 1.0\"," +
            "            \"params\": {" +
            "                \"query_vector\": ?0" +
            "            }" +
            "        }" +
            "    }" +
            "}")
    Page<ESFeed> FalseQueryTrueVectorFalseSubscribeFalseAdvanced(
            List<Float> vector, Pageable pageable);

    // False False True T
    @Query("{" +
            "    \"bool\": {" +
            "        \"must\": {" +
            "            \"query\": {" +
            "                \"match_all\": {}" +
            "            }" +
            "        }," +
            "        \"filter\": {" +
            "            \"terms\": {" +
            "                \"pk\": ?0" +
            "            }" +
            "        }" +
            "    }" +
            "}")
    Page<ESFeed> FalseQueryFalseVectorTrueSubscribeTrueAdvanced(
            List<Long> subscribe, Pageable pageable);

    // False False True F
    @Query("{" +
            "    \"bool\": {" +
            "        \"must\": {" +
            "            \"query\": {" +
            "                \"match_all\": {}" +
            "            }" +
            "        }," +
            "        \"filter\": {" +
            "            \"terms\": {" +
            "                \"pk\": ?0" +
            "            }" +
            "        }" +
            "    }" +
            "}")
    Page<ESFeed> FalseQueryFalseVectorTrueSubscribeFalseAdvanced(
            List<Long> subscribe, Pageable pageable);

    // False False False T
    @Query("{" +
            "    \"match_all\": {}" +
            "}")
    Page<ESFeed> FalseQueryFalseVectorFalseSubscribeTrueAdvanced(
            Pageable pageable);

    // False False False F
    @Query("{" +
            "    \"match_all\": {}" +
            "}")
    Page<ESFeed> FalseQueryFalseVectorFalseSubscribeFalseAdvanced(
            Pageable pageable);
}





