package com.ssafy.trycatch.user.controller.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class UserModifyDto {
    public final String introduction;
    public final String companyName;

    @Builder
    public UserModifyDto(String introduction, String companyName) {
        this.introduction = introduction;
        this.companyName = companyName;
    }
}
