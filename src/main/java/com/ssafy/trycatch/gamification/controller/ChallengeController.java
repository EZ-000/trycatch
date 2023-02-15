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

        // 1. 유저의 진행 상태 업데이트
        final List<MyChallenge> myChallenges = myChallengeService
                .findMyOngoingChallenges(userId, pageable);

        for (MyChallenge myChallenge : myChallenges) {
            myChallengeService.updateMyChallenge(userId, myChallenge);
        }

        // 2. 전체 리스트 조회
        final List<Challenge> challenges = challengeService.findAll();

        final List<FindChallengeResponseDto> responseDtos = new ArrayList<>();
        for (Challenge challenge : challenges) {
            final Long challengeId = challenge.getId();
            final MyChallenge myChallenge = myChallengeService
                    .findMyLatestChallenge(challengeId, userId);

            final FindChallengeResponseDto responseDto;
            if (null != myChallenge.getId()) {
                responseDto = FindChallengeResponseDto.from(
                        challenge,
                        myChallenge);
            } else {
                responseDto = FindChallengeResponseDto.from(challenge);
            }
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
