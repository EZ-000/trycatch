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
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class CreateQuestionRequestDto {

    private Long categoryId;

    private Long authorId;

    private String title;

    private String content;

    private String errorCode;

    private LocalDateTime createdAt;

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
                .errorCode(errorCode)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
