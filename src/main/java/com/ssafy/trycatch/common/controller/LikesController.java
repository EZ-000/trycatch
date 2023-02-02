package com.ssafy.trycatch.common.controller;

import com.ssafy.trycatch.common.controller.dto.LikesRequestDto;
import com.ssafy.trycatch.common.domain.Likes;
import com.ssafy.trycatch.common.domain.TargetType;
import com.ssafy.trycatch.common.service.LikesService;
import com.ssafy.trycatch.common.service.exceptions.LikesDuplicatedException;
import com.ssafy.trycatch.user.domain.User;
import com.ssafy.trycatch.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Nullable;

@RestController
@RequestMapping("/${apiPrefix}/like")
public class LikesController {
    private final LikesService likesService;
    private final UserService userService;

    @Autowired
    public LikesController(LikesService likesService, UserService userService) {
        this.likesService = likesService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity likeTarget(
            @Nullable @AuthenticationPrincipal Long userId,
            @RequestBody LikesRequestDto likesRequestDto
    ) {
        final User user = userService.findUserById(userId);
        final TargetType type = TargetType.valueOf(likesRequestDto.getType());
        final Likes lastLikes = likesService.getLastLikes(user.getId(), likesRequestDto.getId(),type);
        if (lastLikes.getActivated()) throw new LikesDuplicatedException();
        final Likes newLikes = likesRequestDto.newLikes(user);
        likesService.saveLikes(newLikes);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity unlikeTarget(
            @Nullable @AuthenticationPrincipal Long userId,
            @RequestBody LikesRequestDto likesRequestDto
    ) {
        final User user = userService.findUserById(userId);
        final TargetType type = TargetType.valueOf(likesRequestDto.getType());
        final Likes lastLikes = likesService.getLastLikes(user.getId(), likesRequestDto.getId(), type);
        lastLikes.setActivated(!lastLikes.getActivated());
        likesService.saveLikes(lastLikes);
        return ResponseEntity.ok().build();
    }

}
