package com.ssafy.trycatch.common.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikesRepository extends JpaRepository<Likes, Long> {
    Optional<Likes> findByUserIdAndTargetIdAndTargetType(Long userId, Long targetId, TargetType targetType);

}
