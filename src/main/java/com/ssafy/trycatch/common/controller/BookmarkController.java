package com.ssafy.trycatch.common.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.trycatch.common.annotation.AuthUserElseGuest;
import com.ssafy.trycatch.common.controller.dto.BookmarkRequestDto;
import com.ssafy.trycatch.common.domain.Bookmark;
import com.ssafy.trycatch.common.domain.TargetType;
import com.ssafy.trycatch.common.service.BookmarkService;
import com.ssafy.trycatch.common.service.exceptions.BookmarkDuplicatedException;
import com.ssafy.trycatch.qna.service.AnswerService;
import com.ssafy.trycatch.qna.service.QuestionService;
import com.ssafy.trycatch.user.domain.User;
import com.ssafy.trycatch.user.service.UserService;

@RestController
@RequestMapping("/${apiPrefix}/bookmark")
public class BookmarkController {
    private final BookmarkService bookmarkService;
    private final UserService userService;
    private final QuestionService questionService;
    private final AnswerService answerService;

    @Autowired
    public BookmarkController(
            BookmarkService bookmarkService, UserService userService, QuestionService questionService,
            AnswerService answerService
    ) {
        this.bookmarkService = bookmarkService;
        this.userService = userService;
        this.questionService = questionService;
        this.answerService = answerService;
    }

    // 해야함
    @GetMapping
    public ResponseEntity<Void> findMyBookmark(
            @AuthUserElseGuest User requestUser, @RequestParam String type
    ) {
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<Void> bookmarkTarget(
            @AuthUserElseGuest User requestUser, @RequestBody BookmarkRequestDto bookmarkRequestDto
    ) {
        final TargetType type = TargetType.valueOf(bookmarkRequestDto.getType());
        final Bookmark lastBookmark = bookmarkService.getLastBookmark(requestUser.getId(),
                                                                      bookmarkRequestDto.getId(), type);
        if (lastBookmark.getActivated()) {
            throw new BookmarkDuplicatedException();
        }
        final Bookmark newBookmark = bookmarkRequestDto.newBookmark(requestUser);
        bookmarkService.register(newBookmark);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> removeBookmark(
            @AuthUserElseGuest User requestUser, @RequestBody BookmarkRequestDto bookmarkRequestDto
    ) {
        final TargetType type = TargetType.valueOf(bookmarkRequestDto.getType());
        final Bookmark lastBookmark = bookmarkService.getLastBookmark(requestUser.getId(),
                                                                      bookmarkRequestDto.getId(), type);
        lastBookmark.setActivated(!lastBookmark.getActivated());
        bookmarkService.register(lastBookmark);
        return ResponseEntity.ok().build();
    }
}
