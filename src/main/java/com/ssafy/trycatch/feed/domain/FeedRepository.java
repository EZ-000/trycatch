package com.ssafy.trycatch.feed.domain;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedRepository extends JpaRepository<Feed, Long> {

	Optional<Feed> findByEsId(String feedId);
}