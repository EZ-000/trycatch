package com.ssafy.trycatch.common.controller;

import com.ssafy.trycatch.common.controller.dto.LikesRequestDto;
import com.ssafy.trycatch.common.domain.TargetType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/${apiPrefix}/like")
public class LikesController {
    private final TargetType targetType;

    public LikesController(TargetType targetType) {
        this.targetType = targetType;
    }

    @PostMapping
    public void likeTarget(
            @RequestHeader(value = "Authorization", defaultValue = "NONE") String token,
            @RequestBody LikesRequestDto likesRequestDto
    ) {

    }
}
