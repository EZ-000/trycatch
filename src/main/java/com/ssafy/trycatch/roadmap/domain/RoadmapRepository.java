package com.ssafy.trycatch.roadmap.domain;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface RoadmapRepository extends PagingAndSortingRepository<Roadmap, Long> {
	Optional<Roadmap> findByUserId(Long userId);
}