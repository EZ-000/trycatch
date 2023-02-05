package com.ssafy.trycatch.user.domain;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, Long> {

	boolean existsByFollowerIdAndFolloweeId(Long src, Long des);

	Optional<Follow> findByFollower_IdAndFollowee_Id(Long id, Long id1);
}