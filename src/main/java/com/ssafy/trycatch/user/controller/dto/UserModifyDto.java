package com.ssafy.trycatch.user.controller.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class UserModifyDto {
    public final String introduction;
    public final String profileImage;

    @Builder
    public UserModifyDto(String introduction, String profileImage) {
        this.introduction = introduction;
        this.profileImage = profileImage;
    }
}
