package com.ssafy.trycatch.roadmap.controller.dto;

import com.ssafy.trycatch.roadmap.domain.Roadmap;
import com.ssafy.trycatch.user.controller.dto.SimpleUserInfo;

import lombok.Builder;
import lombok.Data;

import static com.ssafy.trycatch.common.infra.config.ConstValues.TZ_SEOUL;

@Data
public class RoadmapListResponseDto {

    public static RoadmapListResponseDto from(
            Roadmap roadmap,
            Boolean isBookmarked,
            Boolean isLiked
    ) {
        return RoadmapListResponseDto.builder()
                .roadmapId(roadmap.getId())
                .author(SimpleUserInfo.from(roadmap.getUser()))
                .title(roadmap.getTitle())
                .tag(roadmap.getTag())
                .isBookmarked(isBookmarked)
                .isLiked(isLiked)
                .likeCount(roadmap.getLikes())
                .createdAt(roadmap.getCreatedAt()
                        .atZone(TZ_SEOUL)
                        .toInstant()
                        .toEpochMilli())
                .updatedAt(roadmap.getUpdatedAt().toEpochMilli())
                .build();
    }

    private final Long roadmapId;
    private final SimpleUserInfo author;
    private final String title;
    private final String tag;
    private final Boolean isBookmarked;
    private final Boolean isLiked;
    private final Integer likeCount;
    private final Long createdAt;
    private final Long updatedAt;
    @Builder
    public RoadmapListResponseDto(
            Long roadmapId,
            SimpleUserInfo author,
            String title,
            String tag,
            Boolean isBookmarked, Boolean isLiked, Integer likeCount, Long createdAt, Long updatedAt) {
        this.roadmapId = roadmapId;
        this.author = author;
        this.title = title;
        this.tag = tag;
        this.isBookmarked = isBookmarked;
        this.isLiked = isLiked;
        this.likeCount = likeCount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
