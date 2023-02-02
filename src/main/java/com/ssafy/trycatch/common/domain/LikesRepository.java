package com.ssafy.trycatch.common.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikesRepository extends CrudRepository<Likes, Long> {
    Optional<Likes> findByUserIdAndTargetIdAndTargetType(Long userId, Long targetId, TargetType targetType);

    List<Likes> streamByUserIdAndTargetIdAndTargetType(Long userId, Long targetId, TargetType targetType);

}
