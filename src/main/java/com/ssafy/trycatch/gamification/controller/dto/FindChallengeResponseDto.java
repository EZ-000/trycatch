package com.ssafy.trycatch.gamification.controller.dto;

import com.ssafy.trycatch.gamification.domain.Challenge;
import com.ssafy.trycatch.gamification.domain.MyChallenge;
import com.ssafy.trycatch.gamification.domain.StatusInfo;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

import static com.ssafy.trycatch.common.infra.config.ConstValues.TZ_SEOUL;


@Data
public class FindChallengeResponseDto implements Serializable {
    public static FindChallengeResponseDto from(
            Challenge challenge,
            MyChallenge myChallenge
    ) {
        return FindChallengeResponseDto.builder()
                .challengeId(challenge.getId())
                .title(challenge.getTitle())
                .content(challenge.getContent())
                .imgSrc(challenge.getImgSrc())
                .state(myChallenge.getStatusInfo())
                .progress(myChallenge.getProgress())
                .startFrom(myChallenge.getStartFrom()
                        .atZone(TZ_SEOUL)
                        .toInstant()
                        .toEpochMilli())
                .endAt(myChallenge.getEndAt()
                        .atZone(TZ_SEOUL)
                        .toInstant()
                        .toEpochMilli())
                .earnedAt(myChallenge.getEarnedAt()
                        .atZone(TZ_SEOUL)
                        .toInstant()
                        .toEpochMilli())
                .build();
    }

    public static FindChallengeResponseDto from(
            Challenge challenge
    ) {
        return FindChallengeResponseDto.builder()
                .challengeId(challenge.getId())
                .title(challenge.getTitle())
                .content(challenge.getContent())
                .imgSrc(challenge.getImgSrc())
                .state(StatusInfo.BEFORE)
                .progress(0L)
                .startFrom(null)
                .endAt(null)
                .earnedAt(null)
                .build();
    }

    private final Long challengeId;
    private final String title;
    private final String content;
    private final String imgSrc;
    private final StatusInfo state;
    private final Long progress;
    private final Long startFrom;
    private final Long endAt;
    private final Long earnedAt;


    @Builder
    public FindChallengeResponseDto(
            Long challengeId,
            String title,
            String content,
            String imgSrc,
            StatusInfo state,
            Long progress,
            Long startFrom,
            Long endAt,
            Long earnedAt
    ) {
        this.challengeId = challengeId;
        this.title = title;
        this.content = content;
        this.imgSrc = imgSrc;
        this.state = state;
        this.progress = progress;
        this.startFrom = startFrom;
        this.endAt = endAt;
        this.earnedAt = earnedAt;
    }
}

