package com.ssafy.trycatch.user.controller.dto;

import java.util.List;

import com.ssafy.trycatch.user.domain.User;

import lombok.Builder;

public class UserDto {
	private Long userId;
	private String userName;
	private String companyName;
	private String introduction;
	private String profileImg;
	private Integer subscriptionCount;
	private Integer followerCount;
	private Integer followeeCount;
	private List<String> tags;
	private Boolean isFollowed;

	@Builder
	public UserDto(Long userId, String userName, String companyName, String introduction, String profileImg,
		Integer subscriptionCount, Integer followerCount, Integer followeeCount, List<String> tags,
		Boolean isFollowed) {
		this.userId = userId;
		this.userName = userName;
		this.companyName = companyName;
		this.introduction = introduction;
		this.profileImg = profileImg;
		this.subscriptionCount = subscriptionCount;
		this.followerCount = followerCount;
		this.followeeCount = followeeCount;
		this.tags = tags;
		this.isFollowed = isFollowed;
	}

	public static UserDto from(User saved, List<String> tagList) {
		return UserDto.builder()
			.userId(saved.getId())
			.userName(saved.getUsername())
			.companyName(saved.getCompany().getName())
			.introduction(saved.getIntroduction())
			.profileImg(saved.getImageSrc())
			.subscriptionCount(saved.getSubscriptions().size())
			.followerCount(saved.getFollowers().size())
			.followeeCount(saved.getFollowees().size())
			.tags(tagList)
			.isFollowed(false)
			.build();
	}
}
