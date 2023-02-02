package com.ssafy.trycatch.qna.controller.dto;

import com.ssafy.trycatch.common.domain.QuestionCategory;
import com.ssafy.trycatch.qna.domain.Question;
import com.ssafy.trycatch.user.domain.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class CreateQuestionRequestDto {
    private String categoryName;
    private Long authorId;
    private String title;
    private String content;
    private String errorCode;

    public Question newQuestion(User user) {
        final User author = user;
        final QuestionCategory category = QuestionCategory.valueOf(categoryName);

        return Question.builder()
                .categoryName(category)
                .user(author)
                .title(title)
                .content(content)
                .errorCode(errorCode)
                .createdAt(LocalDateTime.now())
                .updatedAt(Instant.EPOCH.now())
                .chosen(false)
                .viewCount(0)
                .likes(0)
                .hidden(false)
                .build();
    }
}
