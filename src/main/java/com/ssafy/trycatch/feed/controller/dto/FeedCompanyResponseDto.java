package com.ssafy.trycatch.feed.controller.dto;

import com.ssafy.trycatch.common.domain.Company;
import com.ssafy.trycatch.user.domain.SubscriptionRepository;
import com.ssafy.trycatch.user.domain.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FeedCompanyResponseDto {

    private Long companyId;

    private String logoSrc;

    private String companyName;

    private Boolean isFollowed;

    public static FeedCompanyResponseDto from(Company e, User u, SubscriptionRepository subscriptionRepository) {
        return FeedCompanyResponseDto.builder()
            .companyId(e.getId())
            .logoSrc(e.getIcon())
            .companyName(e.getName())
            .isFollowed(
                subscriptionRepository
                    .findByUserIdAndCompanyId(u.getId(),e.getId()).isPresent()
            ).build();
    }
}