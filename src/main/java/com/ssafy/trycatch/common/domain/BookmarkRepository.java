package com.ssafy.trycatch.common.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface BookmarkRepository extends CrudRepository<Bookmark, Long> {
    Optional<Bookmark> findByUserIdAndTargetIdAndTargetType(Long userId, Long targetId, TargetType targetType);
}
