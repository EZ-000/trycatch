package com.ssafy.trycatch.roadmap.controller.dto;

import com.ssafy.trycatch.roadmap.domain.Roadmap;
import com.ssafy.trycatch.user.controller.dto.SimpleUserInfo;

import lombok.Builder;
import lombok.Data;

import java.time.ZoneId;

@Data
public class RoadmapResponseDto {
    public static RoadmapResponseDto from(
            Roadmap roadmap,
            Boolean isBookmarked,
            Boolean isLiked
    ) {
        return RoadmapResponseDto.builder()
                .roadmapId(roadmap.getId())
                .author(SimpleUserInfo.from(roadmap.getUser()))
                .title(roadmap.getTitle())
                .tag(roadmap.getTag())
                .nodes(roadmap.getNode())
                .edges(roadmap.getEdge())
                .isBookmarked(isBookmarked)
                .isLiked(isLiked)
                .likeCount(roadmap.getLikes())
                .createdAt(roadmap.getCreatedAt()
                        .atZone(ZoneId.of("Asia/Seoul"))
                        .toInstant()
                        .toEpochMilli())
                .updatedAt(roadmap.getUpdatedAt().toEpochMilli())
                .build();
    }

    private final Long roadmapId;
    private final SimpleUserInfo author;
    private final String title;
    private final String tag;
    private final String nodes;
    private final String edges;
    private final Boolean isBookmarked;
    private final Boolean isLiked;
    private final Integer likeCount;
    private final Long createdAt;
    private final Long updatedAt;

    @Builder
    public RoadmapResponseDto(
            Long roadmapId,
            SimpleUserInfo author,
            String title,
            String tag,
            String nodes,
            String edges,
            Boolean isBookmarked,
            Boolean isLiked,
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
        this.isBookmarked = isBookmarked;
        this.isLiked = isLiked;
        this.likeCount = likeCount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
