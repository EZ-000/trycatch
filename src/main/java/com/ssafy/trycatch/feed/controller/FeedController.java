package com.ssafy.trycatch.feed.controller;

import com.ssafy.trycatch.common.annotation.AuthUserElseGuest;
import com.ssafy.trycatch.elasticsearch.domain.ESFeed;
import com.ssafy.trycatch.elasticsearch.domain.ESUser;
import com.ssafy.trycatch.feed.controller.dto.SearchFeedRequestDto;
import com.ssafy.trycatch.feed.controller.dto.SearchFeedRequestDto.FeedSortOption;
import com.ssafy.trycatch.feed.controller.dto.SearchFeedResponseDto;
import com.ssafy.trycatch.feed.service.FeedService;
import com.ssafy.trycatch.user.domain.User;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AbstractPageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.data.domain.Sort.Direction.DESC;

@Slf4j
@RestController
@RequestMapping("/${apiPrefix}/feed")
public class FeedController {

    private final FeedService feedService;

    @Autowired
    public FeedController(FeedService feedService) {
        this.feedService = feedService;
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

        // TODO : 구독 필터 구현, 유저 맞춤 정렬 구현
        final String query = requestDto.getQuery();

        Page<ESFeed> feedPage;
        if (requestDto.getSort() == FeedSortOption.user) {
            // 나와의 관련도순
            Long userId = requestUser.getId();
            feedPage = feedService.searchByQueryAndUser(userId, query, pageable);
        } else if (StringUtils.hasText(query)) {
            if (requestDto.getAdvanced()) {
                // 고급 검색
                feedPage = feedService.advanceSearch(query, pageable);
            } else {
                // 일반 검색
                feedPage = feedService.commonSearch(query, pageable);
            }
        } else {
            feedPage = feedService.findAll(pageable);
        }

        return ResponseEntity.ok(SearchFeedResponseDto.of(feedPage, feedService));
    }
}
