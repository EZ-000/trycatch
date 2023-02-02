package com.ssafy.trycatch.feed.controller.dto;

import lombok.Data;

@Data
public class FeedCompanyResponseDto {

    private Integer companyId;

    private String logoSrc;

    private String companyName;

    private Boolean isFollowed;

    private Integer score;
}
