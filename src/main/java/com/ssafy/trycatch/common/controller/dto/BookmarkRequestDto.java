package com.ssafy.trycatch.common.controller.dto;

import com.ssafy.trycatch.common.domain.Bookmark;
import com.ssafy.trycatch.common.domain.TargetType;
import com.ssafy.trycatch.user.domain.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BookmarkRequestDto {
    private Long id;
    private String type;

    public Bookmark newBookmark(User user) {
        final TargetType targetType = TargetType.valueOf(type);

        return Bookmark.builder().userId(user.getId()).targetId(id).targetType(targetType).activated(true).build();
    }
}
