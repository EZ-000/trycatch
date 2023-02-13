package com.ssafy.trycatch.gamification.controller.dto;

import com.ssafy.trycatch.gamification.domain.Badge;
import com.ssafy.trycatch.gamification.domain.MyBadge;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

import static com.ssafy.trycatch.common.infra.config.ConstValues.TZ_SEOUL;


@Data
public class BadgeResponseDto implements Serializable {


    public static BadgeResponseDto from(
            Badge badge,
            MyBadge myBadge
    ) {
        return BadgeResponseDto.builder()
                .badgeId(badge.getId())
                .title(badge.getTitle())
                .content(badge.getContent())
                .imgSrc(badge.getImgSrc())
                .earnedAt(myBadge.getEarnedAt()
                        .atZone(TZ_SEOUL)
                        .toInstant()
                        .toEpochMilli())
                .build();
    }

    private final Long badgeId;
    private final String title;
    private final String content;
    private final String imgSrc;
    private final Long earnedAt;

    @Builder
    public BadgeResponseDto(Long badgeId, String title, String content, String imgSrc, Long earnedAt) {
        this.badgeId = badgeId;
        this.title = title;
        this.content = content;
        this.imgSrc = imgSrc;
        this.earnedAt = earnedAt;
    }

}

