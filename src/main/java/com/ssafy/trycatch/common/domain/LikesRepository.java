package com.ssafy.trycatch.common.domain;

import org.springframework.data.repository.CrudRepository;

public interface LikesRepository extends CrudRepository<Likes, Long> {
    Likes findByUserIdAndTargetIdAndTargetType(Long userId, Long targetId, String targetType);
}
