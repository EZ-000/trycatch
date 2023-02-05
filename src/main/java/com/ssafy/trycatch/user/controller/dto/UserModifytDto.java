package com.ssafy.trycatch.user.controller.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class UserModifytDto {
	public final String introduction;
	public final String profileImage;

	@Builder
	public UserModifytDto(String introduction, String profileImage) {
		this.introduction = introduction;
		this.profileImage = profileImage;
	}
}
