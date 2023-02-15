package com.ssafy.trycatch.user.controller.dto;

import com.ssafy.trycatch.user.domain.Subscription;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserSubscriptionDto {
	public final Long companyId;
	public final String companyName;
	public final Boolean isSubscribe;
	public final String logoSrc;

	public UserSubscriptionDto(Long companyId, String companyName, Boolean isSubscribe, String logoSrc) {
		this.companyId = companyId;
		this.companyName = companyName;
		this.isSubscribe = isSubscribe;
		this.logoSrc = logoSrc;
	}

	public static UserSubscriptionDto from(
		Subscription subscription) {
		return UserSubscriptionDto.builder()
			.companyId(subscription.getCompany().getId())
			.companyName(subscription.getCompany().getName())
			.logoSrc(subscription.getCompany().getIcon())
			.build();
	}
}
