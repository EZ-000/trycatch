package com.ssafy.trycatch.common.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import com.ssafy.trycatch.common.domain.Likes;
import com.ssafy.trycatch.common.domain.LikesRepository;
import com.ssafy.trycatch.common.domain.TargetType;

@Service
public class LikesService extends CrudService<Likes, Long, LikesRepository> {


    @Autowired
    public LikesService(LikesRepository likesRepository) {
        super(likesRepository);
    }

    public Likes getLikes(Long userId, Long targetId, TargetType targetType) {
        return repository.findByUserIdAndTargetIdAndTargetType(userId, targetId, targetType)
                         .orElseGet(Likes::new);
    }

    public Boolean isLikedByUserAndTarget(@Nullable Long userId, Long targetId, TargetType targetType) {
        return repository.existsByUserIdAndTargetIdAndTargetTypeAndActivatedTrue(userId, targetId, targetType);
    }

    public Likes getLastLikes(Long userId, Long targetId, TargetType targetType) {
        List<Likes> likesList = repository.streamByUserIdAndTargetIdAndTargetType(userId, targetId, targetType);
        final Likes lastLikes;
        if (likesList.size() != 0) {
            lastLikes = likesList.get(likesList.size() - 1);
        } else {
            lastLikes = new Likes(0L, 0L, 0L, TargetType.DEFAULT, false);
        }
        return lastLikes;
    }
}
