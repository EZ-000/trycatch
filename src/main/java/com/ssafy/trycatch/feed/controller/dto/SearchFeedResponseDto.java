package com.ssafy.trycatch.feed.controller.dto;

import com.ssafy.trycatch.common.service.BookmarkService;
import com.ssafy.trycatch.elasticsearch.domain.ESFeed;
import com.ssafy.trycatch.feed.service.FeedService;
import com.ssafy.trycatch.user.domain.User;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static com.ssafy.trycatch.common.domain.TargetType.FEED;

@Slf4j
@Data
public class SearchFeedResponseDto {

    public static SearchFeedResponseDto of(
			Collection<ESFeed> esFeedPage,
			FeedService feedService,
			BookmarkService bookmarkService,
			User requestUser
	) {
        return new SearchFeedResponseDto(esFeedPage.stream()
                                                   .map(entity -> Item.of(entity, feedService, bookmarkService, requestUser))
                                                   .collect(Collectors.toList()));
    }

	private List<Item> feedList;

	public SearchFeedResponseDto(List<Item> feedList) {
		this.feedList = feedList;
	}

	@Builder
	@Data
	static class Item {
		private Long id;

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

		static Item of(
				ESFeed entity,
				FeedService feedService,
				BookmarkService bookmarkService,
				User requestUser) {
			final Long id = feedService.findByESId(entity.getId()).getId();

			return Item.builder()
 				.id(id)
				.feedId(entity.getId())
				.title(entity.getTitle())
				.summary(entity.getSummary())
				.companyName(entity.getName())
				.logoSrc(feedService.findIconByCompany(entity.getPk()))
				.createAt(entity.getPublishDate()
					.format(DateTimeFormatter.ISO_DATE))
				.url(entity.getUrl())
				.tags(entity.getTags())
				.keywords(entity.getKeywords())
				.isBookmarked(bookmarkService
						.isBookmarkByUserAndTarget(requestUser.getId(), id, FEED))
				.thumbnailImage(entity.getThumbnailUrl())
				.build();
		}
	}
}
