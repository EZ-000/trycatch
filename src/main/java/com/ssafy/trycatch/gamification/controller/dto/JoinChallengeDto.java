package com.ssafy.trycatch.gamification.controller.dto;

import com.ssafy.trycatch.gamification.domain.Challenge;
import com.ssafy.trycatch.gamification.domain.MyChallenge;
import com.ssafy.trycatch.gamification.domain.StatusInfo;
import com.ssafy.trycatch.user.domain.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
public class JoinChallengeDto {
    public MyChallenge myNewChallenge(Challenge challenge, User user) {
        return MyChallenge.builder()
                .challenge(challenge)
                .user(user)
                .progress(0L)
                .statusInfo(StatusInfo.ONGOING)
                .startFrom(LocalDateTime.now())
                .endAt(LocalDateTime
                        .now()
                        .plusDays(challenge.getTerm() - 1))
                .earnedAt(LocalDateTime.now())
                .build();
    }
}
