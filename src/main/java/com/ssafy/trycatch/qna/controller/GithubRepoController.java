package com.ssafy.trycatch.qna.controller;

import com.ssafy.trycatch.common.annotation.AuthUserElseGuest;
import com.ssafy.trycatch.qna.controller.dto.GithubRepoRequestDto;
import com.ssafy.trycatch.qna.domain.GithubRepo;
import com.ssafy.trycatch.qna.service.GithubRepoService;
import com.ssafy.trycatch.user.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/${apiPrefix}/github-repo")
public class GithubRepoController {
    private final GithubRepoService githubRepoService;

    @Autowired
    public GithubRepoController(GithubRepoService githubRepoService) {
        this.githubRepoService = githubRepoService;
    }

    @PostMapping
    public ResponseEntity<Void> registerGithubRepo(
            @AuthUserElseGuest User requestUser,
            @RequestBody GithubRepoRequestDto githubRepoRequestDto
            ) {

        final GithubRepo githubRepo = githubRepoRequestDto.newGithubRepo(requestUser);
        githubRepoService.register(githubRepo);

        return ResponseEntity.status(201).build();
    }

    @PutMapping
    public ResponseEntity<Void> putGithubRepo(
            @AuthUserElseGuest User requestUser,
            @RequestBody GithubRepoRequestDto githubRepoRequestDto
    ) {
        githubRepoService.updateGithubRepo(
                requestUser.getId(),
                githubRepoRequestDto.getRepoName(),
                githubRepoRequestDto.getDoCommit());

        return ResponseEntity.status(201).build();
    }
}
