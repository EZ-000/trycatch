package com.ssafy.trycatch.gamification.controller.dto;

import com.ssafy.trycatch.gamification.domain.Challenge;
import com.ssafy.trycatch.gamification.domain.MyChallenge;
import com.ssafy.trycatch.user.domain.User;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class JoinChallengeDto {
    public MyChallenge myNewChallenge(Challenge challenge, User user) {
        return MyChallenge.builder()
                .challenge(challenge)
                .user(user)
                .progress(0L)
                .succeed(false)
                .earnAt(null)
                .build();
    }
}
