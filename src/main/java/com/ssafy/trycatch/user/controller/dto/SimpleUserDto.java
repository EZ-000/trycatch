package com.ssafy.trycatch.user.controller.dto;

import com.ssafy.trycatch.common.domain.Company;
import com.ssafy.trycatch.user.domain.Follow;
import com.ssafy.trycatch.user.domain.User;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

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
            Long userId, String userName, String profileImage, String companyName,
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
        final Set<Follow> followees = requestUser.getFollowees();
        final Company company = author.getCompany();
        final String companyName = null != company ? company.getName() : "";
        return new SimpleUserDto(author.getId(), author.getUsername(), author.getImageSrc(), companyName,
                                 followees.contains(followees.contains(author)) // FIXME
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
