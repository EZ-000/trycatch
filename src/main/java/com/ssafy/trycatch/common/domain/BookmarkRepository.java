package com.ssafy.trycatch.common.domain;

import org.springframework.data.repository.CrudRepository;

public interface BookmarkRepository extends CrudRepository<Bookmark, Long> {
    Bookmark findByUserIdAndTargetIdAndTargetType(Long userId, Long targetId, String targetType);
}
