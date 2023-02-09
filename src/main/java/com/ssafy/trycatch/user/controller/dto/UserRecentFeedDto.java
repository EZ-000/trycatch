package com.ssafy.trycatch.user.controller.dto;

import static com.ssafy.trycatch.common.infra.config.ConstValues.*;

import java.util.List;

import com.ssafy.trycatch.elasticsearch.domain.ESFeed;
import com.ssafy.trycatch.feed.domain.Feed;

import lombok.Builder;
import lombok.Data;

@Data
public class UserRecentFeedDto {
	public final Long feedId;
	public final String title;
	public final String summary;
	public final String companyName;
	public final Long createdAt;
	public final List<String> tags;
	public final Boolean isBookmarked;
	public final String url;
	public final String thumbnailImage;
	public final List<String> keywords;

	@Builder
	public UserRecentFeedDto(Long feedId, String title, String summary, String companyName, Long createdAt,
		List<String> tags, Boolean isBookmarked, String url, String thumbnailImage, List<String> keywords) {
		this.feedId = feedId;
		this.title = title;
		this.summary = summary;
		this.companyName = companyName;
		this.createdAt = createdAt;
		this.tags = tags;
		this.isBookmarked = isBookmarked;
		this.url = url;
		this.thumbnailImage = thumbnailImage;
		this.keywords = keywords;
	}

	public static UserRecentFeedDto from(Feed feed, ESFeed esFeed) {
		return UserRecentFeedDto.builder()
			.feedId(feed.getId())
			.title(feed.getTitle())
			.summary(esFeed.getSummary())
			.companyName(feed.getCompany().getName())
			.createdAt(esFeed.getPublishDate()
				.atStartOfDay()
				.atZone(TZ_SEOUL)
				.toInstant()
				.toEpochMilli())
			.tags(esFeed.getTags())
			.url(esFeed.getUrl())
			.thumbnailImage(esFeed.getThumbnailUrl())
			.keywords(esFeed.getKeywords())
			.build();
	}
}
