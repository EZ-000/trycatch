package com.ssafy.trycatch.elasticsearch.domain.repository;

import com.ssafy.trycatch.elasticsearch.domain.ESFeed;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface ESFeedRepository extends ElasticsearchRepository<ESFeed, String> {

    @Query("{\"match_all\": {}}")
    Page<ESFeed> latest(Pageable pageable);

    List<ESFeed> searchByContent(String content);

    List<ESFeed> findByTags(Collection<String> tags);

    @Query("{\"match\": {\"company_ko\": {\"query\": \"?0\"}}}")
    List<ESFeed> findByCompanyKo(String companyKo);
}





