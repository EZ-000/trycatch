package com.ssafy.trycatch.feed.controller.dto;

import lombok.Data;


@Data
public class FeedCompanyRequestDto {

    private CompanySortOption sort = CompanySortOption.viewCount;

    private Integer size = 5;

    public enum CompanySortOption {
        viewCount, recommend
    }
}
