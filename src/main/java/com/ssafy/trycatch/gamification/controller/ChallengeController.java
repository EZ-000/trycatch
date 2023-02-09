package com.ssafy.trycatch.gamification.controller;

import com.ssafy.trycatch.common.annotation.AuthUserElseGuest;
import com.ssafy.trycatch.gamification.controller.dto.FindChallengeResponseDto;
import com.ssafy.trycatch.gamification.controller.dto.JoinChallengeDto;
import com.ssafy.trycatch.gamification.domain.Challenge;
import com.ssafy.trycatch.gamification.domain.MyChallenge;
import com.ssafy.trycatch.gamification.service.ChallengeService;
import com.ssafy.trycatch.gamification.service.MyChallengeService;
import com.ssafy.trycatch.gamification.service.exception.ChallengeNotFoundException;
import com.ssafy.trycatch.user.domain.User;
import com.ssafy.trycatch.user.service.UserService;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/${apiPrefix}/challenge")
public class ChallengeController {
    private final ChallengeService challengeService;
    private final MyChallengeService myChallengeService;
    private final UserService userService;

    @Autowired
    public ChallengeController(
            ChallengeService challengeService,
            MyChallengeService myChallengeService, UserService userService
    ) {
        this.challengeService = challengeService;
        this.myChallengeService = myChallengeService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<FindChallengeResponseDto>> findChallengeList(
            @ApiParam(hidden = true) @AuthUserElseGuest User requestUser,
            @PageableDefault Pageable pageable
    ) {
        final Long userId = requestUser.getId();
        // 유저가 참여하고 있는 챌린지 리스트만 가져와서 for문 돌면서
        // 챌린지 id를 기준으로 MyChallengeService에서 progress랑 succeed 업데이트
        final List<MyChallenge> myChallenges = myChallengeService
                .findMyChallenges(userId, pageable);


        final List<Challenge> challenges = challengeService.findAll();


        final List<FindChallengeResponseDto> responseDtos = new ArrayList<>();
        for (Challenge challenge : challenges) {
            final Long challengeId = challenge.getId();

            final Boolean isJoined = myChallengeService.IsJoined(challengeId, userId);
            final Boolean isSucceed = myChallengeService.IsSucceed(challengeId, userId);
            final Long progress = myChallengeService.getProgress(challengeId, userId);

            final FindChallengeResponseDto responseDto = FindChallengeResponseDto.from(
                    challenge,
                    isJoined,
                    isSucceed,
                    progress);

            responseDtos.add(responseDto);
        }
        return ResponseEntity.ok(responseDtos);
    }

    @PostMapping("/{challengeId}")
    public ResponseEntity<Void> joinChallenge(
            @PathVariable Long challengeId,
            @AuthUserElseGuest User requestUser

    ) {
        // 게스트 요청 방지
        final Long userId = requestUser.getId();
        userService.findUserById(userId);

        final Challenge challenge = challengeService
                .findById(challengeId)
                .orElseThrow(ChallengeNotFoundException::new);

        final JoinChallengeDto joinChallengeDto = new JoinChallengeDto();
        final MyChallenge myNewChallenge = joinChallengeDto.myNewChallenge(challenge, requestUser);
        myChallengeService.register(myNewChallenge);

        return ResponseEntity.status(201).build();
    }

}
