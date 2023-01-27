package com.ssafy.trycatch.user.controller.dto;


import com.ssafy.trycatch.user.domain.Follow;
import com.ssafy.trycatch.user.domain.User;
import lombok.Builder;

import java.util.Set;

public class FindUserInQNADto {
    public final Long userId;

    public final String image;

    public final String userName;

    public final String companyName;

    public final Boolean isFollow;

    @Builder
    public FindUserInQNADto(Long userId, String image, String userName, String companyName, Boolean isFollow) {
        this.userId  = userId;
        this.image = image;
        this.userName = userName;
        this.companyName = companyName;
        this.isFollow = isFollow;
    }

    // confirmed 필드 수정 후 companyName 추가
    // user = 로그인 유저, author = qna 작성자
    public static FindUserInQNADto from(User author, User user) {

        final Set<Follow> followees = user.getFollowees();

        return FindUserInQNADto.builder()
                .userId(user.getId())
                .image(user.getImage())
                .userName(user.getUsername())
                .isFollow(followees.contains(author.getId()))
                .build();
    }
}
