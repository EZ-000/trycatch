package com.ssafy.trycatch.qna.controller.dto;

import com.ssafy.trycatch.qna.domain.Category;
import com.ssafy.trycatch.qna.domain.Question;
import com.ssafy.trycatch.qna.service.CategoryService;
import com.ssafy.trycatch.user.domain.User;
import com.ssafy.trycatch.user.service.UserService;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
public class PutQuestionRequestDto {

    private Long questionId;

    private String title;

    private String content;

    private Boolean hidden;

    public void putQuestionRequestDto(String title, String content, Boolean hidden) {
        this.title = title;
        this.content = content;
        this.hidden = hidden;
    }

    public Long getId() { return this.questionId; }

    public String getTitle() { return this.title; }

    public String getContent() { return this.content; }

    public boolean getHidden() { return this.hidden; }

    public void setTitle(String title) { this.title = title; }

    public void setContent(String content) { this.content = content; }

    public void setHidden(Boolean hidden) { this.hidden = hidden; }

}
