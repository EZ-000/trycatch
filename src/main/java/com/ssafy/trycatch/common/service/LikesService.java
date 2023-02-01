package com.ssafy.trycatch.common.service;

import com.ssafy.trycatch.common.domain.Likes;
import com.ssafy.trycatch.common.domain.LikesRepository;
import com.ssafy.trycatch.common.domain.TargetType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public class LikesService {

    private final LikesRepository likesRepository;

    @Autowired
    public LikesService(LikesRepository likesRepository) { this.likesRepository = likesRepository; }

    public Likes getLikes(Long userId, Long targetId, TargetType targetType) {
        return likesRepository.findByUserIdAndTargetIdAndTargetType(userId, targetId, targetType).orElseGet(() -> new Likes());
    }
}
