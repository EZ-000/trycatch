package com.ssafy.trycatch.common.controller.dto;

import com.ssafy.trycatch.roadmap.domain.Roadmap;
import com.ssafy.trycatch.user.controller.dto.SimpleUserInfo;
import lombok.Builder;
import lombok.Data;

import static com.ssafy.trycatch.common.infra.config.ConstValues.TZ_SEOUL;

@Data
public class FindBookmarkedRoadmapDto {
    private final Long roadmapId;
    private final SimpleUserInfo author;
    private final String title;
    private final String tag;
    private final String nodes;
    private final String edges;
    private final Integer likeCount;
    private final Long createdAt;
    private final Long updatedAt;


    @Builder
    public FindBookmarkedRoadmapDto(
            Long roadmapId,
            SimpleUserInfo author,
            String title,
            String tag,
            String nodes,
            String edges,
            Integer likeCount,
            Long createdAt,
            Long updatedAt
    ) {
        this.roadmapId = roadmapId;
        this.author = author;
        this.title = title;
        this.tag = tag;
        this.nodes = nodes;
        this.edges = edges;
        this.likeCount = likeCount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static FindBookmarkedRoadmapDto from(Roadmap roadmap) {
        return FindBookmarkedRoadmapDto.builder()
                .roadmapId(roadmap.getId())
                .author(SimpleUserInfo.from(roadmap.getUser()))
                .title(roadmap.getTitle())
                .tag(roadmap.getTag())
                .nodes(roadmap.getNode())
                .edges(roadmap.getEdge())
                .likeCount(roadmap.getLikes())
                .createdAt(roadmap.getCreatedAt()
                        .atZone(TZ_SEOUL)
                        .toInstant()
                        .toEpochMilli())
                .updatedAt(roadmap.getUpdatedAt().toEpochMilli())
                .build();
    }
}
