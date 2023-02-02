package com.ssafy.trycatch.common.service;

import com.ssafy.trycatch.common.controller.dto.LikesRequestDto;
import com.ssafy.trycatch.common.domain.Likes;
import com.ssafy.trycatch.common.domain.LikesRepository;
import com.ssafy.trycatch.common.domain.TargetType;
import com.ssafy.trycatch.common.service.exceptions.LikesNotFoundException;
import com.ssafy.trycatch.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Service
public class LikesService {

    private final LikesRepository likesRepository;

    @Autowired
    public LikesService(LikesRepository likesRepository) { this.likesRepository = likesRepository; }

    public Likes getLikes(Long userId, Long targetId, TargetType targetType) {
        return likesRepository.findByUserIdAndTargetIdAndTargetType(userId, targetId, targetType).orElseGet(() -> new Likes());
    }

    public Likes getLastLikes(Long userId, Long targetId, TargetType targetType) {
        List<Likes> likesList = likesRepository.streamByUserIdAndTargetIdAndTargetType(userId, targetId, targetType);
        final Likes lastLikes;
        if (likesList.size() != 0) {
            lastLikes = likesList.get(likesList.size() - 1);
        }
        else {
            lastLikes = new Likes(0L, 0L, 0L, TargetType.DEFAULT, false);
        }
        return lastLikes;
    }

    public Likes saveLikes(Likes likes) {
        return likesRepository.save(likes);
    }

}
