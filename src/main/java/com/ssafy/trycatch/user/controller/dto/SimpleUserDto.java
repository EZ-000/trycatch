package com.ssafy.trycatch.user.controller.dto;

import com.ssafy.trycatch.common.domain.Company;
import com.ssafy.trycatch.user.domain.Follow;
import com.ssafy.trycatch.user.domain.User;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class SimpleUserDto implements Serializable {
    public static Builder builder() {
        return new Builder();
    }

    public final Long userId;
    public final String userName;
    public final String profileImage;
    public final String companyName;
    public final Boolean isFollowed;

    private SimpleUserDto(
            Long userId,
            String userName,
            String profileImage,
            String companyName,
            Boolean isFollowed
    ) {
        this.userId = userId;
        this.userName = userName;
        this.profileImage = profileImage;
        this.companyName = companyName;
        this.isFollowed = isFollowed;
    }

    // user: 로그인한 사용자, author: 질문/답변 작성자
    private static SimpleUserDto from(User author, User requestUser) {
        // requestUser가 follower로 있는 Follow entity를 찾는다.
        final Set<Follow> followees = requestUser.getFollowers();
        final Set<Long> followeeIds = followees
                .stream()
                .map(Follow::getFollowee)
                .map(User::getId)
                .collect(Collectors.toSet());

        final Company company = author.getCompany();
        final String companyName = null != company ? company.getName() : "";

        return new SimpleUserDto(
                author.getId(),
                author.getUsername(),
                author.getImageSrc(),
                companyName,
                followeeIds.contains(author.getId())
        );
    }

    private static SimpleUserDto from(User author) {
        final Company company = author.getCompany();
        final String companyName = null == company ? "" : company.getName();
        return new SimpleUserDto(author.getId(), author.getUsername(), author.getImageSrc(), companyName,
                                 false
        );
    }

    public static class Builder {
        private User author;
        private User requestUser;

        public Builder author(User author) {
            this.author = author;
            return this;
        }

        public Builder requestUser(User requestUser) {
            this.requestUser = requestUser;
            return this;
        }

        public SimpleUserDto build() {
            if (null == requestUser) {
                return SimpleUserDto.from(author);
            }
            return SimpleUserDto.from(author, requestUser);
        }
    }
}
