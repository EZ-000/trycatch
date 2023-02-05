package com.ssafy.trycatch.roadmap.controller.dto;

import com.ssafy.trycatch.roadmap.domain.Roadmap;
import com.ssafy.trycatch.user.domain.User;

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

    public Roadmap toEntity(User writer) {
        return Roadmap.builder().user(writer).title(title).tag(tag).node(nodes).edge(edges).build();
    }
}
