package com.ssafy.trycatch.common.controller.dto;

import java.util.List;

import com.ssafy.trycatch.common.domain.Company;
import com.ssafy.trycatch.user.controller.dto.UserFeedDto;
import com.ssafy.trycatch.user.domain.User;

import lombok.Builder;
import lombok.Data;

@Data
public class CompanyResponseDto {
	public final Long companyId;
	public final String companyName;
	public final String companyLogo;
	public final String companyBlog;
	public final Boolean isSubscribe;
	public final Integer subscriptionCount;
	public final List<UserFeedDto> companyFeed;

	@Builder
	public CompanyResponseDto(Long companyId, String companyName, String companyLogo, String companyBlog,
		Boolean isSubscribe, Integer subscriptionCount, List<UserFeedDto> companyFeed) {
		this.companyId = companyId;
		this.companyName = companyName;
		this.companyLogo = companyLogo;
		this.companyBlog = companyBlog;
		this.isSubscribe = isSubscribe;
		this.subscriptionCount = subscriptionCount;
		this.companyFeed = companyFeed;
	}

	public static CompanyResponseDto from(Company company, User requestUser, List<UserFeedDto> feedList) {
		boolean isSubscribe =
			company.getSubscriptions()
				.stream()
				.anyMatch(e -> e.getUser().getId() == requestUser.getId());

		return CompanyResponseDto.builder()
			.companyId(company.getId())
			.companyName(company.getName())
			.companyLogo(company.getLogo())
			.companyBlog(company.getBlog())
			.isSubscribe(isSubscribe)
			.subscriptionCount(company.getSubscriptions().size())
			.companyFeed(feedList)
			.build();
	}
}
