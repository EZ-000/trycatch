package com.ssafy.trycatch.user.controller.dto;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.ssafy.trycatch.feed.domain.Feed;
import com.ssafy.trycatch.feed.domain.Read;

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

	public static UserRecentFeedDto from(Read e) {
		Feed feed = e.getFeed();
		String tag = feed.getTags();
		List<String> tags = Collections.emptyList();
		if(false == tag.isEmpty()){
			tags = Arrays.stream(tag.split(",")).collect(Collectors.toList());
		}

		String keyword = feed.getTags();
		List<String> keywords = Collections.emptyList();
		if(false == tag.isEmpty()){
			keywords = Arrays.stream(keyword.split(",")).collect(Collectors.toList());
		}

		return UserRecentFeedDto.builder()
			.feedId(feed.getId())
			.title(feed.getTitle())
			.summary(feed.getSummary())
			.companyName(feed.getCompany().getName())
			.createdAt(0L)
			.tags(tags)
			.url(feed.getUrl())
			.thumbnailImage(feed.getThumbnail())
			.keywords(keywords)
			.build();
	}
}
