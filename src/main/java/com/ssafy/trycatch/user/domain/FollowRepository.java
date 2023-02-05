package com.ssafy.trycatch.user.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {

	boolean existsByFollowerIdAndFolloweeId(Long src, Long des);

	Optional<Follow> findByFollower_IdAndFollowee_Id(Long id, Long id1);
}