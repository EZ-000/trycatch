package com.ssafy.trycatch.feed.controller.dto;

import com.ssafy.trycatch.elasticsearch.domain.ESFeed;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

@Data
public class FindFeedResponseDto {

    private static DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    List<Feed> feedList;

    @Builder
    public FindFeedResponseDto(List<Feed> feedList) {
        this.feedList = feedList;
    }

    @Data
    @Builder
    public static class Feed {

        private String feedId;

        private String title;

        private String summary;

        private String companyName;

        private String createdAt;

        private String url;

        private String thumbnailImage;

        private List<String> tags;

        private List<String> keywords;

        private Boolean isBookmarked;

        public static Feed newDummy(Long id) {
            List<String> tags = List.of("" +id);
            LocalDateTime now = LocalDateTime.now();
            return Feed.builder()
                    .feedId("" + id)
                    .title("title-" + id)
                    .summary("summary-" + id)
                    .companyName("company-" + id)
                    .createdAt("2023-01-01")
                    .url("https://i8e108.p.ssafy.io/")
                    .thumbnailImage("https://i8e108.p.ssafy.io/assets/favicon-1170e8b7.ico")
                    .tags(tags)
                    .build();
        }

        public static Feed newFeed(ESFeed esFeed) {

            return Feed.builder()
                    .feedId(esFeed.getId())
                    .title(esFeed.getTitle())
                    .summary(esFeed.getSummary())
                    .companyName(esFeed.getName())
                    .createdAt(esFeed.getPublishDate().format(dateFormat))
                    .url(esFeed.getUrl())
                    .thumbnailImage(esFeed.getThumbnailUrl())
                    .tags(esFeed.getTags())
                    .keywords(esFeed.getKeywords())
                    .isBookmarked(false) // FIXME
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
