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
import com.ssafy.trycatch.user.domain.User;
import com.ssafy.trycatch.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/${apiPrefix}/like")
public class LikesController {
    private final LikesService likesService;
    private final UserService userService;
    private final QuestionService questionService;
    private final AnswerService answerService;

    @Autowired
    public LikesController(
            LikesService likesService, UserService userService, QuestionService questionService,
            AnswerService answerService
    ) {
        this.likesService = likesService;
        this.userService = userService;
        this.questionService = questionService;
        this.answerService = answerService;
    }

    @PostMapping
    public ResponseEntity<Void> likeTarget(
            @AuthUserElseGuest User requestUser, @RequestBody LikesRequestDto likesRequestDto
    ) {
        final TargetType type = TargetType.valueOf(likesRequestDto.getType());
        final Likes lastLikes = likesService.getLastLikes(requestUser.getId(), likesRequestDto.getId(), type);
        if (lastLikes.getActivated()) {
            throw new LikesDuplicatedException();
        }
        final Likes newLikes = likesRequestDto.newLikes(requestUser);
        likesService.register(newLikes);
        if (type == TargetType.QUESTION) {
            final Question question = questionService.findQuestionById(likesRequestDto.getId());
            question.setLikes(question.getLikes() + 1);
            questionService.saveQuestion(question);
        } else if (type == TargetType.ANSWER) {
            final Answer answer = answerService.findById(likesRequestDto.getId());
            answer.setLikes(answer.getLikes() + 1);
            answerService.saveAnswer(answer);
        }
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> unlikeTarget(
            @AuthUserElseGuest User requestUser, @RequestBody LikesRequestDto likesRequestDto
    ) {
        final TargetType type = TargetType.valueOf(likesRequestDto.getType());
        final Likes lastLikes = likesService.getLastLikes(requestUser.getId(), likesRequestDto.getId(), type);
        lastLikes.setActivated(!lastLikes.getActivated());
        likesService.register(lastLikes);
        if (type == TargetType.QUESTION) {
            final Question question = questionService.findQuestionById(likesRequestDto.getId());
            question.setLikes(question.getLikes() - 1);
            questionService.saveQuestion(question);
        } else if (type == TargetType.ANSWER) {
            final Answer answer = answerService.findById(likesRequestDto.getId());
            answer.setLikes(answer.getLikes() - 1);
            answerService.saveAnswer(answer);
        }
        return ResponseEntity.ok().build();
    }
}
