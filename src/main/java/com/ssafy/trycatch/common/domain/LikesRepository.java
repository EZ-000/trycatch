package com.ssafy.trycatch.common.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikesRepository extends JpaRepository<Likes, Long> {
    Optional<Likes> findByUserIdAndTargetIdAndTargetType(Long userId, Long targetId, TargetType targetType);

    List<Likes> streamByUserIdAndTargetIdAndTargetType(Long userId, Long targetId, TargetType targetType);
}
