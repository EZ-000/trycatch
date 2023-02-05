package com.ssafy.trycatch.roadmap.controller.dto;

import com.ssafy.trycatch.roadmap.domain.Roadmap;
import lombok.Builder;
import lombok.Data;

@Data
public class RoadmapRequestDto {
	private String title;
	private String tag;
	private String nodes;
	private String edges;

	@Builder
	public RoadmapRequestDto(String title, String tag, String nodes, String edges) {
		this.title = title;
		this.tag = tag;
		this.nodes = nodes;
		this.edges = edges;
	}

	public Roadmap toEntity() {
		return Roadmap.builder()
			.user(null)
			.title(title)
			.tag(tag)
			.node(nodes)
			.edge(edges)
			.build();
	}
}
