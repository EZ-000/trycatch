package com.ssafy.trycatch.qna.controller.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class PutAnswerRequestDto {
    private Long answerId;
    private String content;
    private Boolean hidden;
}
