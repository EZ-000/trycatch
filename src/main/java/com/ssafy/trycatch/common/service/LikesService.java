package com.ssafy.trycatch.common.service;

import com.ssafy.trycatch.common.domain.Likes;
import com.ssafy.trycatch.common.domain.LikesRepository;
import com.ssafy.trycatch.common.domain.TargetType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikesService extends CrudService<Likes, Long, LikesRepository> {

    @Autowired
    public LikesService(LikesRepository likesRepository) {
        super(likesRepository);
    }

    public Likes getLikes(Long userId, Long targetId, TargetType targetType) {
        return repository
                .findByUserIdAndTargetIdAndTargetType(userId, targetId, targetType)
                .orElseGet(Likes::new);
    }
}
