package com.ssafy.trycatch.qna.service;

import com.ssafy.trycatch.common.service.CrudService;
import com.ssafy.trycatch.qna.domain.GithubRepo;
import com.ssafy.trycatch.qna.domain.GithubRepoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class GithubRepoService extends CrudService<GithubRepo, Long, GithubRepoRepository> {

    @Autowired
    public GithubRepoService(
            GithubRepoRepository githubRepoRepository
    ) {
        super(githubRepoRepository);
    }

    public Boolean isRepoChecked(Long userId) {
        return repository.existsByUserId(userId);
    }

    public GithubRepo findByUser(Long userId) {
        return repository.findByUserId(userId)
                .orElse(new GithubRepo());
    }

    @Transactional
    public void updateGithubRepo(Long userId, String repoName, Boolean doCommit) {
        final GithubRepo githubRepo = repository.findByUserId(userId)
                .orElse(new GithubRepo());

        githubRepo.setUserId(userId);
        githubRepo.setRepoName(repoName);
        githubRepo.setDoCommit(doCommit);
        repository.save(githubRepo);
    }
}
