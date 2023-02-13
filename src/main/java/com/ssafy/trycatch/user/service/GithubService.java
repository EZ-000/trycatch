package com.ssafy.trycatch.user.service;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;


@Slf4j
@Service
public class GithubService {

    private final Function<GHRepository, Set<String>> getLanguages = ghRepository -> {
        try {
            return ghRepository.listLanguages()
                    .keySet();
        } catch (IOException e) {
            return Collections.emptySet();
        }
    };

    public Mono<Set<String>> getLanguages(String githubToken) throws IOException {
        final GitHub gitHub = GitHub.connectUsingOAuth(githubToken);
        return Mono.just(
                gitHub.getMyself()
                        .listRepositories()
                        .toList()
                        .stream()
                        .map(getLanguages)
                        .flatMap(Set::stream)
                        .collect(Collectors.toSet())
        );
    }

    public List<Float> getVector(String doc) {
        return WebClient.create("http://try-catch.duckdns.org:8000")
                .get()
                .uri("/doc2vec?doc={doc}", doc)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Float>>() {})
                .block();
    }

    public Mono<List<Float>> getVectorFromToken(String githubToken) throws Exception {
        return getLanguages(githubToken)
                .map(l -> String.join(" ", l))
                .map(this::getVector);
    }

    /**
     * @param githubToken 깃허브 Oauth 토큰
     * @param repoName 저장소 이름
     * @param fileName  경로를 포함한 파일 이름
     * @param content   파일 내용
     * @throws IOException
     */
    public void createFile(String githubToken, String repoName, String fileName, String content) throws IOException {
        final GitHub gitHub = GitHub.connectUsingOAuth(githubToken);
        final String myName = gitHub.getMyself().getLogin();

        GHRepository repo;
        try {
            repo = gitHub.getRepository(myName + "/" + repoName);
        } catch (IOException e) {
            repo = gitHub.createRepository(repoName).create();
        }
        repo.createContent()
                .content(content)
                .path(fileName)
                .message("create file")
                .commit();
    }
}
