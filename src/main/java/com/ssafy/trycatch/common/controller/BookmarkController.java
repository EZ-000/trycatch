package com.ssafy.trycatch.common.controller;

import com.ssafy.trycatch.common.annotation.AuthUserElseGuest;
import com.ssafy.trycatch.common.controller.dto.BookmarkRequestDto;
import com.ssafy.trycatch.common.controller.dto.FindBookmarkedQuestionDto;
import com.ssafy.trycatch.common.controller.dto.FindBookmarkedRoadmapDto;
import com.ssafy.trycatch.common.domain.Bookmark;
import com.ssafy.trycatch.common.domain.TargetType;
import com.ssafy.trycatch.common.service.BookmarkService;
import com.ssafy.trycatch.common.service.exceptions.InvalidBookmarkRequestException;
import com.ssafy.trycatch.qna.domain.Question;
import com.ssafy.trycatch.qna.service.QuestionService;
import com.ssafy.trycatch.qna.service.exceptions.QuestionNotFoundException;
import com.ssafy.trycatch.roadmap.domain.Roadmap;
import com.ssafy.trycatch.roadmap.service.RoadmapService;
import com.ssafy.trycatch.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/${apiPrefix}/bookmark")
public class BookmarkController {
    private final BookmarkService bookmarkService;
    private final QuestionService questionService;
    private final RoadmapService roadmapService;

    @Autowired
    public BookmarkController(
            BookmarkService bookmarkService,
            QuestionService questionService,
            RoadmapService roadmapService) {
        this.bookmarkService = bookmarkService;
        this.questionService = questionService;
        this.roadmapService = roadmapService;
    }

    @PostMapping
    public ResponseEntity<Void> bookmarkTarget(
            @AuthUserElseGuest User requestUser, @RequestBody BookmarkRequestDto bookmarkRequestDto
    ) {
        if (null == questionService.findQuestionById(bookmarkRequestDto.getId())) {
            throw new QuestionNotFoundException();
        }

        final TargetType type = TargetType.valueOf(bookmarkRequestDto.getType());
        final Bookmark lastBookmark = bookmarkService.getLastBookmark(requestUser.getId(),
                                                                      bookmarkRequestDto.getId(),
                                                                      type);
        if (lastBookmark.getActivated()) {
            throw new InvalidBookmarkRequestException();
        }

        final Bookmark newBookmark = bookmarkRequestDto.newBookmark(requestUser);
        bookmarkService.register(newBookmark);
        return ResponseEntity.ok()
                             .build();
    }

    @SuppressWarnings("ConstantConditions") // FIXME : !lastBookmark.getActivated()
    @PutMapping
    public ResponseEntity<Void> removeBookmark(
            @AuthUserElseGuest User requestUser, @RequestBody BookmarkRequestDto bookmarkRequestDto
    ) {
        final TargetType type = TargetType.valueOf(bookmarkRequestDto.getType());
        final Bookmark lastBookmark = bookmarkService.getLastBookmark(requestUser.getId(),
                                                                      bookmarkRequestDto.getId(),
                                                                      type);
        if (!lastBookmark.getActivated()) {
            throw new InvalidBookmarkRequestException();
        }

        lastBookmark.setActivated(!lastBookmark.getActivated());
        bookmarkService.register(lastBookmark);
        return ResponseEntity.ok()
                             .build();
    }

    /**
     * @param requestUser 요청자
     * @return 유저가 북마크한 질문 리스트를 FindBookmarkedQuestionResponseDto로 반환
     */
    @GetMapping("/question")
    public ResponseEntity<List<FindBookmarkedQuestionDto>> findBookmarkedQuestions(
            @AuthUserElseGuest User requestUser
    ) {
        // 북마크 서비스에서 userId, targetType, activated 로 활성화된 질문 List<Bookmark> 반환
        List<Bookmark> activatedBookmarks = bookmarkService
                .getActivatedBookmarks(requestUser.getId(), TargetType.QUESTION, true);

        // List<Bookmark>을 List<Question>으로 변환
        List<Question> bookmarkedQuestions = activatedBookmarks
                .stream()
                .map(Bookmark::getTargetId)
                .map(questionService::findQuestionById)
                .collect(Collectors.toList());

        // List<Question>을 List<FindBookmarkedQuestionDto>로 변환
        List<FindBookmarkedQuestionDto> bookmarkedQuestionsResponse = bookmarkedQuestions
                .stream()
                .map(FindBookmarkedQuestionDto::from)
                .collect(Collectors.toList());

        return ResponseEntity.ok(bookmarkedQuestionsResponse);
    }

    /**
     * @param requestUser 요청자
     * @return 유저가 북마크한 로드맵 리스트를 FindBookmarkedRoadmapResponseDto 로 반환
     */
    @GetMapping("/roadmap")
    public ResponseEntity<List<FindBookmarkedRoadmapDto>> findBookmarkedRoadmaps(
            @AuthUserElseGuest User requestUser
    ) {
        // 북마크 서비스에서 userId, targetType, activated 로 활성화된 로드맵 북마크 리스트 List<Roadmap> 반환
        List<Bookmark> activatedBookmarks = bookmarkService
                .getActivatedBookmarks(requestUser.getId(), TargetType.ROADMAP, true);

        // List<Bookmark>을 List<Roadmap>으로 변환
        List<Roadmap> bookmarkedRoadmaps = activatedBookmarks
                .stream()
                .map(Bookmark::getTargetId)
                .map(roadmapService::findByRoadmapId)
                .collect(Collectors.toList());

        // List<Roadmap>을 List<FindBookmarkedRoadmapDto>로 변환
        List<FindBookmarkedRoadmapDto> bookmarkedRoadmapsResponse = bookmarkedRoadmaps
                .stream()
                .map(FindBookmarkedRoadmapDto::from)
                .collect(Collectors.toList());

        return ResponseEntity.ok(bookmarkedRoadmapsResponse);
    }
}
