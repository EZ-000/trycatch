package com.ssafy.trycatch.common.service;

import com.ssafy.trycatch.common.domain.Likes;
import com.ssafy.trycatch.common.domain.LikesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikesService {

    private final LikesRepository likesRepository;

    @Autowired
    public LikesService(LikesRepository likesRepository) { this.likesRepository = likesRepository; }

    public Likes getLikes(Long userId, Long targetId, String targetType) {
        return likesRepository.findByUserIdAndTargetIdAndTargetType(userId, targetId, targetType);
    }
}
