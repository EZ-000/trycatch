package com.ssafy.trycatch.qna.controller.dto;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

import com.ssafy.trycatch.common.domain.QuestionCategory;
import com.ssafy.trycatch.qna.domain.Question;
import com.ssafy.trycatch.user.domain.User;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateQuestionRequestDto {
    private String category;
    private Long authorId;
    private String title;
    private String content;
    private String errorCode;
    private List<String> tags;

    public Question newQuestion(User user) {
        final User author = user;
        final QuestionCategory categoryName = QuestionCategory.valueOf(category);

        return Question.builder().categoryName(categoryName).user(author).title(title).content(content)
                       .errorCode(errorCode).createdAt(LocalDateTime.now()).updatedAt(Instant.now()).chosen(
                        false).viewCount(0).likes(0).hidden(false).tags(String.join(",", tags)).build();
    }
}