package com.ssafy.trycatch.qna.controller.dto;

import com.ssafy.trycatch.qna.domain.GithubRepo;
import com.ssafy.trycatch.user.domain.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GithubRepoRequestDto {
    private String repoName;
    private Boolean doCommit;

    public GithubRepo newGithubRepo(User user) {
        return GithubRepo.builder()
                .userId(user.getId())
                .repoName(repoName)
                .doCommit(doCommit)
                .build();
    }
}
