package com.ssafy.trycatch.common.controller.dto;

import com.ssafy.trycatch.common.domain.Likes;
import com.ssafy.trycatch.common.domain.TargetType;
import com.ssafy.trycatch.user.domain.User;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LikesRequestDto {
    private Long id;
    private String type;

    public Likes newLikes(User user) {
        final TargetType targetType = TargetType.valueOf(type);

        return Likes.builder().userId(user.getId()).targetId(id).targetType(targetType).activated(true).build();
    }
}

