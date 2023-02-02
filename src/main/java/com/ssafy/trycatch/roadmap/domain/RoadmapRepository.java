package com.ssafy.trycatch.roadmap.domain;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoadmapRepository extends JpaRepository<Roadmap, Long> {
	Optional<Roadmap> findByUserId(Long userId);
}