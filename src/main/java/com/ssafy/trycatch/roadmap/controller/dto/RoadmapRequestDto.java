package com.ssafy.trycatch.roadmap.controller.dto;

import com.ssafy.trycatch.roadmap.domain.Roadmap;

import lombok.Builder;
import lombok.Data;

@Data
public class RoadmapRequestDto {
	private Long userId;
	private String node;
	private String edge;

	@Builder
	public RoadmapRequestDto(Long userId, String node, String edge) {
		this.userId = userId;
		this.node = node;
		this.edge = edge;
	}

	public Roadmap newRoadmap() {
		return Roadmap.builder()
			.userId(userId)
			.node(node)
			.edge(edge)
			.build();
	}
}
