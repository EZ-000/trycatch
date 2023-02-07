package com.ssafy.trycatch.feed.controller.dto;

import com.ssafy.trycatch.elasticsearch.domain.ESFeed;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class SearchFeedResponseDto {

    public static SearchFeedResponseDto of(Page<ESFeed> esFeedPage) {
        return new SearchFeedResponseDto(esFeedPage.stream()
                                                   .map(Item::of)
                                                   .collect(Collectors.toList()));
    }

    private List<Item> feedList;

    public SearchFeedResponseDto(List<Item> feedList) {
        this.feedList = feedList;
    }

    @Builder
    @Data
    static class Item {
        private String feedId;

        private String title;

        private String summary;

        private String companyName;

        private String logoSrc;

        private String createAt;

        private String url;

        private List<String> tags;

        private List<String> keywords;

        private Boolean isBookmarked;

        private String thumbnailImage;

        static Item of(ESFeed entity) {
            return Item.builder()
                    .feedId(entity.getId())
                    .title(entity.getTitle())
                    .summary(entity.getSummary())
                    .companyName(entity.getName())
                    .logoSrc(entity.getName())  // FIXME
                    .createAt(entity.getPublishDate()
                                    .format(DateTimeFormatter.ISO_DATE))
                    .url(entity.getUrl())
                    .tags(entity.getTags())
                    .keywords(entity.getKeywords())
                    .isBookmarked(false) // FIXME
                    .thumbnailImage(entity.getThumbnailUrl())
                    .build();
        }
    }
}
