package com.ssafy.trycatch.qna.controller.dto;

import com.ssafy.trycatch.common.domain.QuestionCategory;
import com.ssafy.trycatch.qna.domain.Question;
import com.ssafy.trycatch.user.domain.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class CreateQuestionRequestDto {

    private QuestionCategory categoryName;

    private Long authorId;

    private String title;

    private String content;

    private String errorCode;

    private LocalDateTime createdAt;

    public Question newQuestion(
            QuestionCategory categoryName,
            User user
    ) {
        final User author = user;
        return Question.builder()
                .categoryName(categoryName)
                .user(author)
                .title(title)
                .content(content)
                .errorCode(errorCode)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
