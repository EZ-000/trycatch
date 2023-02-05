package com.ssafy.trycatch.common.service;

import com.ssafy.trycatch.common.controller.dto.LikesRequestDto;
import com.ssafy.trycatch.common.domain.Likes;
import com.ssafy.trycatch.common.domain.LikesRepository;
import com.ssafy.trycatch.common.domain.TargetType;
import com.ssafy.trycatch.feed.domain.ReadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class LikesService extends CrudService<Likes, Long, LikesRepository> {

    private final ReadRepository readRepository;

    @Autowired
    public LikesService(LikesRepository likesRepository,
                        ReadRepository readRepository) {
        super(likesRepository);
        this.readRepository = readRepository;
    }

    public Likes getLikes(Long userId, Long targetId, TargetType targetType) {
        return repository
                .findByUserIdAndTargetIdAndTargetType(userId, targetId, targetType)
                .orElseGet(Likes::new);
    }

    public Boolean isLikedByUserAndTarget(@Nullable Long userId, Long targetId, TargetType targetType) {
        if (null == userId) {
            return false;
        }
        return repository.existsByUserIdAndTargetIdAndTargetTypeAndActivatedTrue(userId, targetId, targetType);
    }

    public Likes getLastLikes(Long userId, Long targetId, TargetType targetType) {
        List<Likes> likesList = repository.streamByUserIdAndTargetIdAndTargetType(userId, targetId, targetType);
        final Likes lastLikes;
        if (likesList.size() != 0) {
            lastLikes = likesList.get(likesList.size() - 1);
        }
        else {
            lastLikes = new Likes(0L, 0L, 0L, TargetType.DEFAULT, false);
        }
        return lastLikes;
    }
}
