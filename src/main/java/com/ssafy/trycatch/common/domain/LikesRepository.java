package com.ssafy.trycatch.common.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikesRepository extends JpaRepository<Likes, Long> {

    Optional<Likes> findFirstByUserIdAndTargetIdAndTargetTypeOrderByIdDesc(Long userId, Long targetId, TargetType targetType);

    boolean existsByUserIdAndTargetIdAndTargetTypeAndActivatedTrue(
            Long userId, Long targetId,
            TargetType targetType
    );
}
