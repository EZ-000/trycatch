package com.ssafy.trycatch.qna.controller.dto;

import com.ssafy.trycatch.qna.domain.Category;
import com.ssafy.trycatch.qna.domain.CategoryRepository;
import com.ssafy.trycatch.qna.domain.Question;
import com.ssafy.trycatch.qna.service.CategoryService;
import com.ssafy.trycatch.qna.service.exceptions.CategoryNotFoundException;
import com.ssafy.trycatch.user.domain.User;
import com.ssafy.trycatch.user.domain.UserRepository;
import com.ssafy.trycatch.user.service.UserService;
import com.ssafy.trycatch.user.service.exceptions.UserNotFoundException;
import lombok.Builder;
import lombok.Data;

@Data
public class CreateQuestionRequestDto {

    private final Long categoryId;

    private final Long authorId;

    private final String title;

    private final String content;

    private final Boolean hidden;

    @Builder
    public CreateQuestionRequestDto(Long categoryId, Long authorId, String title, String content, Boolean hidden) {
        this.categoryId = categoryId;
        this.authorId = authorId;
        this.title = title;
        this.content = content;
        this.hidden = hidden;
    }

    public Question newQuestion(
            CategoryService categoryService,
            UserService userService
    ) {
        final Category category = categoryService.findCategoryById(categoryId);
        final User author = userService.findUserById(authorId);
        return Question.builder()
                .category(category)
                .user(author)
                .title(title)
                .content(content)
                .hidden(hidden)
                .build();
    }
}
