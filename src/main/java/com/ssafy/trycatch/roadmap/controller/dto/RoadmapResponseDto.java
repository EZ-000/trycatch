package com.ssafy.trycatch.roadmap.controller.dto;

import com.ssafy.trycatch.roadmap.domain.Roadmap;

import lombok.Builder;
import lombok.Data;

@Data
public class RoadmapResponseDto {
	private String node;
	private String edge;

	@Builder
	public RoadmapResponseDto(String node, String edge) {
		this.node = node;
		this.edge = edge;
	}

	public static RoadmapResponseDto from(Roadmap roadmap) {
		return RoadmapResponseDto.builder()
			.node(roadmap.getNode())
			.edge(roadmap.getEdge())
			.build();
	}
}
