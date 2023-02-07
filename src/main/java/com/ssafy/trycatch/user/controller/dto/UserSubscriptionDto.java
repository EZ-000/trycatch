package com.ssafy.trycatch.user.controller.dto;

import com.ssafy.trycatch.user.domain.Subscription;

import lombok.Builder;
import lombok.Data;

@Data
public class UserSubscriptionDto {
	public final Long companyId;
	public final String companyName;
	public final Boolean isSubscribe;

	@Builder
	public UserSubscriptionDto(Long companyId, String companyName, Boolean isSubscribe) {
		this.companyId = companyId;
		this.companyName = companyName;
		this.isSubscribe = isSubscribe;
	}

	public static UserSubscriptionDto from(Subscription subscription) {
		return UserSubscriptionDto.builder()
			.companyId(subscription.getCompany().getId())
			.companyName(subscription.getCompany().getName())
			.build();
	}
}
