package com.ssafy.trycatch.common.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface LikesRepository extends CrudRepository<Likes, Long> {
    Optional<Likes> findByUserIdAndTargetIdAndTargetType(Long userId, Long targetId, String targetType);

}
