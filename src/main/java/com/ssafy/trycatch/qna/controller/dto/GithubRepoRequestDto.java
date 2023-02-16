package com.ssafy.trycatch.qna.controller.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GithubRepoRequestDto {
    private String repoName;
    private Boolean repoChecked;

}
