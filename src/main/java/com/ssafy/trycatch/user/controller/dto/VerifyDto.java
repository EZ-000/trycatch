package com.ssafy.trycatch.user.controller.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class VerifyDto {
    public static VerifyDto from(Boolean result) {
        return VerifyDto.builder()
                .isVerified(result)
                .build();
    }

    public final Boolean isVerified;

    @Builder
    public VerifyDto(Boolean isVerified) {
        this.isVerified = isVerified;
    }
}
