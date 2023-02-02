package com.ssafy.trycatch.user.controller.dto;

import com.ssafy.trycatch.common.service.CompanyService;
import com.ssafy.trycatch.user.domain.User;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
public class FindUserInQNANotLoginDto implements Serializable {
    public final Long userId;
    public final String userName;
    public final String profileImage;
    public final String companyName;
    public final Boolean isFollowed;

    @Builder
    public FindUserInQNANotLoginDto(Long userId, String userName, String profileImage, String companyName, Boolean isFollowed) {
        this.userId = userId;
        this.userName = userName;
        this.profileImage = profileImage;
        this.companyName = companyName;
        this.isFollowed = isFollowed;
    }

    // user: 로그인한 사용자, author: 질문/답변 작성자
    public static FindUserInQNANotLoginDto from(User author, CompanyService companyService) {

        final String companyName;
        if (null == author.getCompany()) {
            companyName = "";
        }
        else {
            companyName = companyService
                    .findById(author.getCompany().getId())
                    .orElseThrow()
                    .getName();
        }

        return FindUserInQNANotLoginDto.builder()
                .userId(author.getId())
                .userName(author.getUsername())
                .profileImage(author.getImageSrc())
                .companyName(companyName)
                .isFollowed(false)
                .build();
    }
}
