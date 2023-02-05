package com.ssafy.trycatch.qna.controller.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PutAnswerRequestDto {
    private Long answerId;
    private String content;
    private Boolean hidden;
}
