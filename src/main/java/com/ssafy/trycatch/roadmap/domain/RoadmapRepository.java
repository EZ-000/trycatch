package com.ssafy.trycatch.roadmap.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoadmapRepository extends JpaRepository<Roadmap, Long> {
    Optional<Roadmap> findByUserId(Long userId);
}