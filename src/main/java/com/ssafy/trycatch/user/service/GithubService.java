package com.ssafy.trycatch.user.service;

import java.util.Collection;
import java.util.List;
import java.util.Set;


class GithubRepository {

}

public class GithubService {

    public String findGithubIdByNodeId(String nodeId, String githubToken) {
        throw new RuntimeException("NotImplement");
    }

    public List<GithubRepository> findRepositoryListByGithubId(String githubId, String githubToken) {
        throw new RuntimeException("NotImplement");
    }

    public Set<String> findLanguageFromGithubRepository(Collection<GithubRepository> repositories) {
        throw new RuntimeException("NotImplement");
    }
}
