package com.ssafy.trycatch.qna.controller.dto;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PutQuestionRequestDto {
    private Long questionId;
    private String category;
    private String title;
    private String content;
    private String errorCode;
    private List<String> tags;
    private Boolean hidden;
}
