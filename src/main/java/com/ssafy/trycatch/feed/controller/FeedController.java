package com.ssafy.trycatch.feed.controller;

import com.ssafy.trycatch.common.annotation.AuthUserElseGuest;
import com.ssafy.trycatch.common.domain.Company;
import com.ssafy.trycatch.common.service.BookmarkService;
import com.ssafy.trycatch.elasticsearch.domain.ESFeed;
import com.ssafy.trycatch.feed.controller.dto.FeedCompanyResponseDto;
import com.ssafy.trycatch.feed.controller.dto.SearchFeedRequestDto;
import com.ssafy.trycatch.feed.controller.dto.SearchFeedRequestDto.FeedSortOption;
import com.ssafy.trycatch.feed.controller.dto.SearchFeedResponseDto;
import com.ssafy.trycatch.feed.service.FeedService;
import com.ssafy.trycatch.feed.service.exception.FeedNotFoundException;
import com.ssafy.trycatch.user.domain.SubscriptionRepository;
import com.ssafy.trycatch.user.domain.User;
import com.ssafy.trycatch.user.service.exceptions.UserNotFoundException;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.data.domain.Sort.Direction.DESC;

@Slf4j
@RestController
@RequestMapping("/${apiPrefix}/feed")
public class FeedController {

    private final FeedService feedService;
    private final BookmarkService bookmarkService;
    private final SubscriptionRepository subscriptionRepository;

    @Autowired
    public FeedController(
            FeedService feedService,
            BookmarkService bookmarkService,
            SubscriptionRepository subscriptionRepository
    ) {
        this.feedService = feedService;
        this.bookmarkService = bookmarkService;
        this.subscriptionRepository = subscriptionRepository;
    }

    private static Pageable newPageable(Integer page, Integer size, Sort sort) {
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
                return sort;
            }

            @NonNull
            @Override
            public Pageable withPage(int pageNumber) {
                return newPageable(pageNumber, size, sort);
            }
        };
    }

    // localhost:8080/v1/feed/search?query=qr
    @SuppressWarnings("all")
    @GetMapping("/search")
    public ResponseEntity<SearchFeedResponseDto> search(
            @ApiParam(hidden = true)
            @AuthUserElseGuest User requestUser,
            SearchFeedRequestDto requestDto
    ) {

        final String query = requestDto.getQuery();
        final FeedSortOption sortOption = requestDto.getSort();
        final Sort sort = sortOption == FeedSortOption.user ? Sort.unsorted() : Sort.by(DESC, sortOption.name);
        final Pageable pageable = newPageable(requestDto.getPage(), requestDto.getSize(), sort);

        final boolean recommend = requestDto.getSort().equals(FeedSortOption.user);
        final boolean hasQuery = StringUtils.hasText(query);
        final boolean advanced = requestDto.getAdvanced();
        final boolean subscribeOnly = requestDto.getSubscribe();

        Page<ESFeed> feedPage;
        // T T T T
        if ( hasQuery && recommend && subscribeOnly && advanced ) {
            feedPage = feedService.TrueQueryTrueVectorTrueSubscribeTrueAdvanced(
                    requestUser, query, pageable
            );
        }
        // T T T F
        else if ( hasQuery && recommend && subscribeOnly && !advanced ) {
            feedPage = feedService.TrueQueryTrueVectorTrueSubscribeFalseAdvanced(
                    requestUser, query, pageable
            );
        }
        // T T F T
        else if ( hasQuery && recommend && !subscribeOnly && advanced ) {
            feedPage = feedService.TrueQueryTrueVectorFalseSubscribeTrueAdvanced(
                    requestUser, query, pageable
            );
        }
        // T T F F
        else if ( hasQuery && recommend && !subscribeOnly && !advanced ) {
            feedPage = feedService.TrueQueryTrueVectorFalseSubscribeFalseAdvanced(
                    requestUser, query, pageable
            );
        }
        // T F T T
        else if ( hasQuery && !recommend && subscribeOnly && advanced ) {
            feedPage = feedService.TrueQueryFalseVectorTrueSubscribeTrueAdvanced(
                    requestUser, query, pageable
            );
        }
        // T F T F
        else if ( hasQuery && !recommend && subscribeOnly && !advanced ) {
            feedPage = feedService.TrueQueryFalseVectorTrueSubscribeFalseAdvanced(
                    requestUser, query, pageable
            );
        }
        // T F F T
        else if ( hasQuery && !recommend && !subscribeOnly && advanced ) {
            feedPage = feedService.TrueQueryFalseVectorFalseSubscribeTrueAdvanced(
                    query, pageable
            );
        }
        // T F F F
        else if ( hasQuery && !recommend && !subscribeOnly && !advanced ) {
            feedPage = feedService.TrueQueryFalseVectorFalseSubscribeFalseAdvanced(
                    query, pageable
            );
        }
        // F T T T
        else if ( !hasQuery && recommend && subscribeOnly && advanced ) {
            feedPage = feedService.FalseQueryTrueVectorTrueSubscribeTrueAdvanced(
                    requestUser, pageable
            );
        }
        // F T T F
        else if ( !hasQuery && recommend && subscribeOnly && !advanced ) {
            feedPage = feedService.FalseQueryTrueVectorTrueSubscribeFalseAdvanced(
                    requestUser, pageable
            );
        }
        // F T F T
        else if ( !hasQuery && recommend && !subscribeOnly && advanced ) {
            feedPage = feedService.FalseQueryTrueVectorFalseSubscribeTrueAdvanced(
                    requestUser, pageable
            );
        }
        // F T F F
        else if ( !hasQuery && recommend && !subscribeOnly && !advanced ) {
            feedPage = feedService.FalseQueryTrueVectorFalseSubscribeFalseAdvanced(
                    requestUser, pageable
            );
        }
        // F F T T
        else if ( !hasQuery && !recommend && subscribeOnly && advanced ) {
            feedPage = feedService.FalseQueryFalseVectorTrueSubscribeTrueAdvanced(
                    requestUser, pageable
            );
        }
        // F F T F
        else if ( !hasQuery && !recommend && subscribeOnly && !advanced ) {
            feedPage = feedService.FalseQueryFalseVectorTrueSubscribeFalseAdvanced(
                    requestUser, pageable
            );
        }
        // F F F T
        else if ( !hasQuery && !recommend && !subscribeOnly && advanced ) {
            feedPage = feedService.FalseQueryFalseVectorFalseSubscribeTrueAdvanced(
                    pageable
            );
        }
        // F F F F
        // !recommend && !hasQuery && !advanced && !subscribeOnly
        else {
            feedPage = feedService.FalseQueryFalseVectorFalseSubscribeFalseAdvanced(
                    pageable
            );
        }

        return ResponseEntity.ok(SearchFeedResponseDto.of(
                feedPage.toList(), feedService, bookmarkService, requestUser));
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

    @GetMapping("/company")
    public ResponseEntity<List<FeedCompanyResponseDto>> readFeed(
        @AuthUserElseGuest User requestUser) {
        List<Company> companyList = feedService.getTop5CompanyList();

        return ResponseEntity.ok(companyList.stream()
            .map(e->FeedCompanyResponseDto.from(e,requestUser, subscriptionRepository))
            .collect(Collectors.toList()));
    }
}
