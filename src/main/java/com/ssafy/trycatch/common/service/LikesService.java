package com.ssafy.trycatch.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import com.ssafy.trycatch.common.domain.Likes;
import com.ssafy.trycatch.common.domain.LikesRepository;
import com.ssafy.trycatch.common.domain.TargetType;

@Service
public class LikesService extends CrudService<Likes, Long, LikesRepository> {

    @Autowired
    public LikesService(
            LikesRepository likesRepository
    ) {
        super(likesRepository);
    }

    public Likes getLikes(Long userId, Long targetId, TargetType targetType) {
        return repository.findFirstByUserIdAndTargetIdAndTargetTypeOrderByIdDesc(userId, targetId, targetType)
                         .orElse(new Likes(0L, 0L, 0L, TargetType.DEFAULT, false));
    }

    public Boolean isLikedByUserAndTarget(@Nullable Long userId, Long targetId, TargetType targetType) {
        return repository.existsByUserIdAndTargetIdAndTargetTypeAndActivatedTrue(userId, targetId, targetType);
    }
}
