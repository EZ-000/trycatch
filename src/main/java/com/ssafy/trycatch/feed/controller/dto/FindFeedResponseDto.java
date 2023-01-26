package com.ssafy.trycatch.feed.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

@Data
public class FindFeedResponseDto {

    List<Feed> feedList;

    @Builder
    public FindFeedResponseDto(List<Feed> feedList) {
        this.feedList = feedList;
    }

    @AllArgsConstructor
    @Data
    static class FeedTag {
        private Long id;
        private String tagName;

        public static FeedTag newDummy(Long id) {
            return new FeedTag(id, "tag-" + id);
        }
    }

    @Data
    @Builder
    static class Feed {

        private Long feedId;

        private String title;

        private String content;

        private String companyName;

        private Long publishedTimestamp;

        private Boolean isBookmarked;

        private String blogURL;

        private String thumbnailImage;

        private List<FeedTag> tags;

        public static Feed newDummy(Long id) {
            List<FeedTag> tags = List.of(FeedTag.newDummy(id));
            LocalDateTime now = LocalDateTime.now();
            return Feed.builder()
                    .feedId(id)
                    .title("title-" + id)
                    .content("content-" + id)
                    .companyName("company-" + id)
                    .publishedTimestamp(Timestamp.valueOf(now).getTime())
                    .isBookmarked(false)
                    .blogURL("https://i8e108.p.ssafy.io/")
                    .thumbnailImage("https://i8e108.p.ssafy.io/assets/favicon-1170e8b7.ico")
                    .tags(tags)
                    .build();
        }
    }

    public static FindFeedResponseDto newDummy(Integer amount) {
        final List<Feed> feeds = LongStream.range(1, amount + 1)
                .mapToObj(Feed::newDummy)
                .collect(Collectors.toList());
        return new FindFeedResponseDto(feeds);
    }
}
