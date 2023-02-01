package com.ssafy.trycatch.roadmap.controller.dto;

import com.ssafy.trycatch.roadmap.domain.Roadmap;
import com.ssafy.trycatch.user.controller.dto.SimpleUserInfo;

import lombok.Builder;
import lombok.Data;

@Data
public class RoadmapResponseDto {
	private SimpleUserInfo simpleUser;
	private String title;
	private String tag;
	private String nodes;
	private String edges;

	@Builder
	public RoadmapResponseDto(SimpleUserInfo simpleUser, String title, String tag, String nodes, String edges) {
		this.simpleUser = simpleUser;
		this.title = title;
		this.tag = tag;
		this.nodes = nodes;
		this.edges = edges;
	}

	public static RoadmapResponseDto from(Roadmap roadmap) {
		return RoadmapResponseDto.builder()
			.simpleUser(SimpleUserInfo.from(roadmap.getUser()))
			.title(roadmap.getTitle())
			.tag(roadmap.getTag())
			.nodes(roadmap.getNode())
			.edges(roadmap.getEdge())
			.build();
	}
}
