package com.ssafy.trycatch.user.controller.dto;

import com.ssafy.trycatch.common.domain.Company;
import com.ssafy.trycatch.user.domain.Follow;
import com.ssafy.trycatch.user.domain.User;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

@Data
public class SimpleUserDto implements Serializable {
    public final Long userId;
    public final String userName;
    public final String profileImage;
    public final String companyName;
    public final Boolean isFollowed;

    @Builder
    public SimpleUserDto(Long userId, String userName, String profileImage, String companyName, Boolean isFollowed) {
        this.userId = userId;
        this.userName = userName;
        this.profileImage = profileImage;
        this.companyName = companyName;
        this.isFollowed = isFollowed;
    }

    // user: 로그인한 사용자, author: 질문/답변 작성자
    public static SimpleUserDto from(User author, User requestUser) {

        final Set<Follow> followees = requestUser.getFollowees();

        final Company company = author.getCompany();
        final String companyName = null != company ? company.getName() : "";

        return SimpleUserDto.builder()
                .userId(author.getId())
                .userName(author.getUsername())
                .profileImage(author.getImageSrc())
                .companyName(companyName)
                .isFollowed(followees.contains(author)) // FIXME
                .build();
    }

    public static SimpleUserDto from(User author) {
        final Company company = author.getCompany();
        final String companyName = null == company ? "" : company.getName();
        return SimpleUserDto.builder()
                .userId(author.getId())
                .userName(author.getUsername())
                .profileImage(author.getImageSrc())
                .companyName(companyName)
                .isFollowed(false)
                .build();
    }
}
