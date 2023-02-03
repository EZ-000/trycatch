package com.ssafy.trycatch.common.controller;

import com.ssafy.trycatch.common.controller.dto.BookmarkRequestDto;
import com.ssafy.trycatch.common.domain.Bookmark;
import com.ssafy.trycatch.common.domain.TargetType;
import com.ssafy.trycatch.common.service.BookmarkService;
import com.ssafy.trycatch.common.service.exceptions.BookmarkDuplicatedException;
import com.ssafy.trycatch.qna.domain.Question;
import com.ssafy.trycatch.qna.service.AnswerService;
import com.ssafy.trycatch.qna.service.QuestionService;
import com.ssafy.trycatch.user.domain.User;
import com.ssafy.trycatch.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Nullable;

@RestController
@RequestMapping("/${apiPrefix}/bookmark")
public class BookmarkController {
    private final BookmarkService bookmarkService;
    private final UserService userService;
    private final QuestionService questionService;
    private final AnswerService answerService;

    @Autowired
    public BookmarkController(BookmarkService bookmarkService, UserService userService, QuestionService questionService, AnswerService answerService) {
        this.bookmarkService = bookmarkService;
        this.userService = userService;
        this.questionService = questionService;
        this.answerService = answerService;
    }

    // 해야함
    @GetMapping
    public ResponseEntity findMyBookmark(
            @Nullable @AuthenticationPrincipal Long userId,
            @RequestParam String type
    ) {
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity bookmarkTarget(
            @Nullable @AuthenticationPrincipal Long userId,
            @RequestBody BookmarkRequestDto bookmarkRequestDto
    ) {
        final User user = userService.findUserById(userId);
        final TargetType type = TargetType.valueOf(bookmarkRequestDto.getType());
        final Bookmark lastBookmark = bookmarkService.getLastBookmark(userId, bookmarkRequestDto.getId(), type);
        if (lastBookmark.getActivated()) throw new BookmarkDuplicatedException();
        final Bookmark newBookmark = bookmarkRequestDto.newBookmark(user);
        bookmarkService.register(newBookmark);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity removeBookmark(
            @Nullable @AuthenticationPrincipal Long userId,
            @RequestBody BookmarkRequestDto bookmarkRequestDto
    ) {
        final User user = userService.findUserById(userId);
        final TargetType type = TargetType.valueOf(bookmarkRequestDto.getType());
        final Bookmark lastBookmark = bookmarkService.getLastBookmark(userId, bookmarkRequestDto.getId(), type);
        lastBookmark.setActivated(!lastBookmark.getActivated());
        bookmarkService.register(lastBookmark);
        return ResponseEntity.ok().build();
    }
}
