package com.ssafy.trycatch.gamification.controller.dto;

import com.ssafy.trycatch.gamification.domain.Challenge;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.Instant;


@Data
public class FindChallengeResponseDto implements Serializable {
    public static FindChallengeResponseDto from(
            Challenge challenge,
            Boolean isJoined,
            Boolean isSucceed,
            Long progress
    ) {
        return FindChallengeResponseDto.builder()
                .challengeId(challenge.getId())
                .title(challenge.getTitle())
                .content(challenge.getTitle())
                .imgSrc(challenge.getImgSrc())
                .startFrom(challenge.getStartFrom())
                .endAt(challenge.getEndAt())
                .isJoined(isJoined)
                .isSucceed(isSucceed)
                .progress(progress)
                .build();
    }

    private final Long challengeId;
    private final String title;
    private final String content;
    private final String imgSrc;
    private final Instant startFrom;
    private final Instant endAt;
    private final Boolean isJoined;
    private final Boolean isSucceed;
    private final Long progress;


    @Builder
    public FindChallengeResponseDto(
            Long challengeId,
            String title,
            String content,
            String imgSrc,
            Instant startFrom,
            Instant endAt,
            Boolean isJoined,
            Boolean isSucceed,
            Long progress
    ) {
        this.challengeId = challengeId;
        this.title = title;
        this.content = content;
        this.imgSrc = imgSrc;
        this.startFrom = startFrom;
        this.endAt = endAt;
        this.isJoined = isJoined;
        this.isSucceed = isSucceed;
        this.progress = progress;
    }
}

