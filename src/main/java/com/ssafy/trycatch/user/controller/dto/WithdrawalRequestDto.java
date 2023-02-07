package com.ssafy.trycatch.user.controller.dto;

import com.ssafy.trycatch.user.domain.Withdrawal;
import lombok.Builder;
import lombok.Data;

@Data
public class WithdrawalRequestDto {
    private Long userId;
    private String content;

    @Builder
    public WithdrawalRequestDto(Long userId, String content) {
        this.userId = userId;
        this.content = content;
    }

    public Withdrawal toEntity() {
        return Withdrawal.builder()
                .id(userId)
                .reason(content)
                .build();
    }
}
