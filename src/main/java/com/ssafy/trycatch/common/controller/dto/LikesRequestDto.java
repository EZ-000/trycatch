package com.ssafy.trycatch.common.controller.dto;

import com.ssafy.trycatch.common.domain.Likes;
import com.ssafy.trycatch.common.domain.TargetType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LikesRequestDto {
    private Long id;
    private String type;

    public Likes newLikes() {
        final TargetType
    }
}
