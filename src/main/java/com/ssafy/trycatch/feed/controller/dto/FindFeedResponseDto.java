package com.ssafy.trycatch.feed.controller.dto;

import com.ssafy.trycatch.elasticsearch.domain.ESFeed;
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

    @Data
    @Builder
    public static class Feed {

        private String feedId;

        private String title;

        private String content;

        private String companyName;

        private String createdAt;

        private Boolean isBookmarked;

        private String blogURL;

        private String thumbnailImage;

        private List<String> tags;

        public static Feed newDummy(Long id) {
            List<String> tags = List.of("" +id);
            LocalDateTime now = LocalDateTime.now();
            return Feed.builder()
                    .feedId("" + id)
                    .title("title-" + id)
                    .content("content-" + id)
                    .companyName("company-" + id)
                    .createdAt("2023-01-01")
                    .isBookmarked(false)
                    .blogURL("https://i8e108.p.ssafy.io/")
                    .thumbnailImage("https://i8e108.p.ssafy.io/assets/favicon-1170e8b7.ico")
                    .tags(tags)
                    .build();
        }

        public static Feed newFeed(ESFeed esFeed) {

            List<String> images = esFeed.getImages();
            String imageUrl = "https://i8e108.p.ssafy.io/assets/favicon-1170e8b7.ico";
            if (!images.isEmpty()) {
                imageUrl = images.get(0);
            }

            return Feed.builder()
                    .feedId(esFeed.getId())
                    .title(esFeed.getTitle())
                    .content(esFeed.getContent())
                    .companyName(esFeed.getCompanyKo())
                    .createdAt(esFeed.getCreatedAt())
                    .isBookmarked(false)
                    .blogURL(esFeed.getUrl())
                    .thumbnailImage(imageUrl)
                    .tags(esFeed.getTags())
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
