package com.ssafy.trycatch.user.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    boolean existsByFollowerIdAndFolloweeId(Long followerId, Long FolloweeId);
}