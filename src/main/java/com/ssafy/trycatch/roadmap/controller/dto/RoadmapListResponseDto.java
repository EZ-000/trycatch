package com.ssafy.trycatch.roadmap.controller.dto;

import com.ssafy.trycatch.roadmap.domain.Roadmap;
import com.ssafy.trycatch.user.controller.dto.SimpleUserInfo;

import lombok.Builder;
import lombok.Data;

@Data
public class RoadmapListResponseDto {
	private SimpleUserInfo author;
	private String title;
	private String tag;

	@Builder
	public RoadmapListResponseDto(SimpleUserInfo author, String title, String tag) {
		this.author = author;
		this.title = title;
		this.tag = tag;
	}

	public static RoadmapListResponseDto from(Roadmap roadmap) {
		return RoadmapListResponseDto.builder()
			.author(SimpleUserInfo.from(roadmap.getUser()))
			.title(roadmap.getTitle())
			.tag(roadmap.getTag())
			.build();
	}
}
