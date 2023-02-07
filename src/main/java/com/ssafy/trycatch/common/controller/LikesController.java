package com.ssafy.trycatch.common.controller;

import com.ssafy.trycatch.common.annotation.AuthUserElseGuest;
import com.ssafy.trycatch.common.controller.dto.LikesRequestDto;
import com.ssafy.trycatch.common.domain.Likes;
import com.ssafy.trycatch.common.domain.TargetType;
import com.ssafy.trycatch.common.service.LikesService;
import com.ssafy.trycatch.common.service.exceptions.LikesDuplicatedException;
import com.ssafy.trycatch.qna.domain.Answer;
import com.ssafy.trycatch.qna.domain.Question;
import com.ssafy.trycatch.qna.service.AnswerService;
import com.ssafy.trycatch.qna.service.QuestionService;
import com.ssafy.trycatch.roadmap.domain.Roadmap;
import com.ssafy.trycatch.roadmap.service.RoadmapService;
import com.ssafy.trycatch.user.domain.User;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/${apiPrefix}/like")
public class LikesController {
    private final LikesService likesService;
    private final QuestionService questionService;
    private final AnswerService answerService;
    private final RoadmapService roadmapService;

    @Autowired
    public LikesController(
            LikesService likesService,
            QuestionService questionService,
            AnswerService answerService,
            RoadmapService roadmapService) {
        this.likesService = likesService;
        this.questionService = questionService;
        this.answerService = answerService;
        this.roadmapService = roadmapService;
    }

    /**
     * @param requestUser 로그인된 유저
     * @param likesRequestDto 좋아요 요청 dto
     * @return 생성 성공 시 201 Created 반환
     */
    @PostMapping
    public ResponseEntity<Void> likeTarget(
            @ApiParam(hidden = true) @AuthUserElseGuest User requestUser,
            @RequestBody LikesRequestDto likesRequestDto
    ) {
        // 마지막 좋아요의 활성화 여부 확인 (중복 방지)
        final TargetType type = TargetType
                .valueOf(likesRequestDto.getType());

        final Likes lastLikes = likesService
                .getLikes(requestUser.getId(), likesRequestDto.getId(), type);

        if (lastLikes.getActivated()) {
            throw new LikesDuplicatedException();
        }

        // 좋아요 생성과 저장
        final Likes newLikes = likesRequestDto.newLikes(requestUser);
        likesService.register(newLikes);

        // TargetType에 따라 좋아요 수 증가
        if (type == TargetType.QUESTION) {
            final Question question = questionService
                    .findQuestionById(likesRequestDto.getId());

            question.setLikes(question.getLikes() + 1);
            questionService.saveQuestion(question);

        } else if (type == TargetType.ANSWER) {
            final Answer answer = answerService
                    .findById(likesRequestDto.getId());

            answer.setLikes(answer.getLikes() + 1);
            answerService.saveAnswer(answer);

        } else if (type == TargetType.ROADMAP) {
            final Roadmap roadmap = roadmapService
                    .findByRoadmapId(likesRequestDto.getId());

            roadmap.setLikes(roadmap.getLikes() + 1);
            roadmapService.register(roadmap);
        }

        return ResponseEntity.status(201)
                             .build();
    }

    /**
     * @param requestUser 로그인된 유저
     * @param likesRequestDto 좋아요 요청 dto
     * @return 수정 성공 시 204 No Content 응답
     */
    @PutMapping
    public ResponseEntity<Void> unlikeTarget(
            @ApiParam(hidden = true) @AuthUserElseGuest User requestUser,
            @RequestBody LikesRequestDto likesRequestDto
    ) {
        final TargetType type = TargetType
                .valueOf(likesRequestDto.getType());

        // 마지막 likes를 가져와서 활성화 상태를 false로 변경
        final Likes lastLikes = likesService
                .getLikes(requestUser.getId(), likesRequestDto.getId(), type);
        lastLikes.setActivated(!lastLikes.getActivated());
        likesService.register(lastLikes);

        // TargetType에 따라 좋아요 수 감소
        if (type == TargetType.QUESTION) {
            final Question question = questionService
                    .findQuestionById(likesRequestDto.getId());

            question.setLikes(question.getLikes() - 1);
            questionService.saveQuestion(question);

        } else if (type == TargetType.ANSWER) {
            final Answer answer = answerService
                    .findById(likesRequestDto.getId());

            answer.setLikes(answer.getLikes() - 1);
            answerService.saveAnswer(answer);
        } else if (type == TargetType.ROADMAP) {
            final Roadmap roadmap = roadmapService
                    .findByRoadmapId(likesRequestDto.getId());

            roadmap.setLikes(roadmap.getLikes() - 1);
            roadmapService.register(roadmap);
        }

        return ResponseEntity.status(204)
                             .build();
    }
}
