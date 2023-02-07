package com.ssafy.trycatch.user.controller.dto;

import com.ssafy.trycatch.user.domain.User;
import lombok.Builder;
import lombok.Data;
import org.apache.kafka.common.security.oauthbearer.secured.ValidateException;

@Data
public class SimpleUserInfo {
    public static SimpleUserInfo from(User user) {
        if (null == user.getCompany()) {throw new ValidateException("Null Company");}

        return SimpleUserInfo.builder()
                .userId(user.getId())
                .userName(user.getUsername())
                .profileImage(user.getImageSrc())
                .companyName(user.getCompany()
                                 .getName())
                .isFollowed(false)
                .build();
    }

    private Long userId;
    private String userName;
    private String profileImage;
    private String companyName;
    private Boolean isFollowed;

    @Builder
    public SimpleUserInfo(
            Long userId, String userName, String profileImage, String companyName, Boolean isFollowed
    ) {
        this.userId = userId;
        this.userName = userName;
        this.profileImage = profileImage;
        this.companyName = companyName;
        this.isFollowed = isFollowed;
    }

    public static SimpleUserInfo from(User user, Boolean flag) {
        if (null == user.getCompany()) {throw new ValidateException("Null Company");}

        return SimpleUserInfo.builder()
            .userId(user.getId())
            .userName(user.getUsername())
            .profileImage(user.getImageSrc())
            .companyName(user.getCompany()
                .getName())
            .isFollowed(flag)
            .build();
    }
}
