package com.ssafy.trycatch.common.controller.dto;

import com.ssafy.trycatch.elasticsearch.domain.ESFeed;
import com.ssafy.trycatch.feed.domain.Feed;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Data
public class FindBookmarkedFeedDto implements Serializable {
    private static DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final Long id;
    private final String feedId;
    private final String title;
    private final String content;
    private final String createdAt;
    private final String companyName;
    private final List<String> tags;
    private final String url;
    private final List<String> keywords;

    @Builder
    public FindBookmarkedFeedDto(
            Long id, String feedId,
            String title,
            String content,
            String createdAt,
            String companyName,
            List<String> tags,
            String url,
            List<String> keywords
    ) {
        this.id = id;
        this.feedId = feedId;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.companyName = companyName;
        this.tags = tags;
        this.url = url;
        this.keywords = keywords;
    }

    public static FindBookmarkedFeedDto from(
            Feed feed,
            ESFeed esFeed
    ) {
        return FindBookmarkedFeedDto.builder()
                .id(feed.getId())
                .feedId(esFeed.getId())
                .title(feed.getTitle())
                .content(esFeed.getContent())
                .createdAt(esFeed.getPublishDate()
                        .format(dateFormat))
                .companyName(esFeed.getName())
                .tags(esFeed.getTags())
                .url(esFeed.getUrl())
                .keywords(esFeed.getKeywords())
                .build();
    }
}
