package com.ssafy.trycatch.qna.controller.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PopularTagsResponseDto implements Serializable {

    public static PopularTagsResponseDto from(List<String> tags) {
        return PopularTagsResponseDto.builder()
                .tags(tags)
                .build();
    }

    private final List<String> tags;

    @Builder
    public PopularTagsResponseDto(List<String> tags) {
        this.tags = tags;
    }
}
