package com.ssafy.trycatch.common.controller.dto;

import java.util.List;

import com.ssafy.trycatch.common.domain.Company;
import com.ssafy.trycatch.feed.controller.dto.FindFeedResponseDto;
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
	public final List<FindFeedResponseDto> companyFeed;

	@Builder
	public CompanyResponseDto(Long companyId, String companyName, String companyLogo, String companyBlog,
		Boolean isSubscribe, Integer subscriptionCount, List<FindFeedResponseDto> companyFeed) {
		this.companyId = companyId;
		this.companyName = companyName;
		this.companyLogo = companyLogo;
		this.companyBlog = companyBlog;
		this.isSubscribe = isSubscribe;
		this.subscriptionCount = subscriptionCount;
		this.companyFeed = companyFeed;
	}

	@Builder

	public static CompanyResponseDto from(Company company, User requestUser) {
		boolean isSubscribe =
			company.getSubscriptions()
				.stream()
				.filter(e -> e.getUser().getId() == requestUser.getId())
				.findAny().isPresent();

		return CompanyResponseDto.builder()
			.companyId(company.getId())
			.companyName(company.getName())
			.companyLogo(company.getLogo())
			.companyBlog(company.getBlog())
			.isSubscribe(isSubscribe)
			.subscriptionCount(company.getSubscriptions().size())
			.build();
	}
}
