package com.ssafy.trycatch.elasticsearch.domain.repository;

import com.ssafy.trycatch.elasticsearch.domain.ESUser;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ESUserRepository extends ElasticsearchRepository<ESUser, String> {

    Optional<ESUser> findByUid(Long uid);
}
