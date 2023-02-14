package com.ssafy.trycatch.feed.controller;

import com.ssafy.trycatch.common.annotation.AuthUserElseGuest;
import com.ssafy.trycatch.common.service.BookmarkService;
import com.ssafy.trycatch.elasticsearch.domain.ESFeed;
import com.ssafy.trycatch.feed.controller.dto.SearchFeedRequestDto;
import com.ssafy.trycatch.feed.controller.dto.SearchFeedRequestDto.FeedSortOption;
import com.ssafy.trycatch.feed.controller.dto.SearchFeedResponseDto;
import com.ssafy.trycatch.feed.service.FeedService;
import com.ssafy.trycatch.feed.service.exception.FeedNotFoundException;
import com.ssafy.trycatch.user.domain.User;
import com.ssafy.trycatch.user.service.exceptions.UserNotFoundException;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AbstractPageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

import static org.springframework.data.domain.Sort.Direction.DESC;

@Slf4j
@RestController
@RequestMapping("/${apiPrefix}/feed")
public class FeedController {

    private final FeedService feedService;
    private final BookmarkService bookmarkService;

    @Autowired
    public FeedController(
            FeedService feedService,
            BookmarkService bookmarkService
    ) {
        this.feedService = feedService;
        this.bookmarkService = bookmarkService;
    }

    private static Pageable newPageable(Integer page, Integer size, FeedSortOption sort) {
        return new AbstractPageRequest(page, size) {

            @NonNull
            @Override
            public Pageable next() {
                return newPageable(page + 1, size, sort);
            }

            @NonNull
            @Override
            public Pageable previous() {
                return newPageable(page - 1, size, sort);
            }

            @NonNull
            @Override
            public Pageable first() {
                return newPageable(0, size, sort);
            }

            @NonNull
            @Override
            public Sort getSort() {
                return Sort.by(DESC, sort.name);
            }

            @NonNull
            @Override
            public Pageable withPage(int pageNumber) {
                return newPageable(pageNumber, size, sort);
            }
        };
    }

    // localhost:8080/v1/feed/search?query=qr
    @GetMapping("/search")
    public ResponseEntity<SearchFeedResponseDto> search(
            @ApiParam(hidden = true) @AuthUserElseGuest User requestUser, SearchFeedRequestDto requestDto
    ) {

        final Pageable pageable = newPageable(requestDto.getPage(), requestDto.getSize(), requestDto.getSort());
        final String query = requestDto.getQuery();

        Collection<ESFeed> feedPage;
        if (requestDto.getSort() == FeedSortOption.user) {
            // 나와의 관련도순
            Long userId = requestUser.getId(); // FIXME
//            Long userId = 1L;
            if (StringUtils.hasText(query)) {
                feedPage = feedService.searchByQueryAndUser(userId, query, pageable);
            } else {
                feedPage = feedService.searchByUser(userId, pageable);
            }
        } else if (StringUtils.hasText(query)) {
            if (requestDto.getAdvanced()) {
                // 고급 검색
                feedPage = feedService.advanceSearch(query, pageable).toList();
            } else {
                // 일반 검색
                feedPage = feedService.commonSearch(query, pageable).toList();
            }
        } else {
            feedPage = feedService.findAll(pageable).toList();
        }

        return ResponseEntity.ok(SearchFeedResponseDto.of(
                feedPage, feedService, bookmarkService, requestUser));
    }

    @PostMapping("/read")
    public ResponseEntity<String> readFeed(
        @RequestParam Long feedId,
        @AuthUserElseGuest User requestUser) {
        try {
            feedService.readFeed(requestUser, feedId);
            return ResponseEntity.ok().build();
        } catch (UserNotFoundException e) {
            return ResponseEntity.ok("해당 사용자가 없음");
        } catch (FeedNotFoundException e) {
            return ResponseEntity.ok("해당 Feed가 없음");
        }
    }
}
