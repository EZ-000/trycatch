package com.ssafy.trycatch.common.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    Optional<Bookmark> findByUserIdAndTargetIdAndTargetType(Long userId, Long targetId, TargetType targetType);

    List<Bookmark> streamByUserIdAndTargetIdAndTargetType(Long userId, Long targetId, TargetType targetType);

    boolean existsByUserIdAndTargetIdAndTargetTypeAndActivatedTrue(Long userId, Long targetId, TargetType targetType);



}
