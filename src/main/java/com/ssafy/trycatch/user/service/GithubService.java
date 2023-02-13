package com.ssafy.trycatch.user.service;

import lombok.extern.slf4j.Slf4j;
import org.kohsuke.github.*;
import org.kohsuke.github.GHEventPayload.Push;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


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
     *
     * @param githubToken 깃허브 Oauth 토큰
     * @param repoName 저장소 이름
     * @param fileName  경로를 포함한 파일 이름
     * @param content   파일 내용
     * @throws IOException 통신 오류
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

    public Integer countEvent(
            String githubToken,
            Function<GHEventInfo, Condition> validator,
            Function<GHEventInfo, Integer> counter
    ) throws IOException {
        final GitHub gitHub = GitHub.connectUsingOAuth(githubToken);
        final GHMyself myself = gitHub.getMyself();
        final PagedIterator<GHEventInfo> ghEventInfoPagedIterator = myself.listEvents()
                ._iterator(10);

        int count = 0;
        while (ghEventInfoPagedIterator.hasNext()) {
            for (GHEventInfo eventInfo : ghEventInfoPagedIterator.nextPage()) {
                switch (validator.apply(eventInfo)) {
                    case STOP:
                        return count;
                    case PASS:
                        break;
                    case TARGET:
                        count += counter.apply(eventInfo);
                        break;
                    default:
                        throw new IllegalStateException();
                }
            }
        }

        return count;
    }

    public Integer countCommitEvent(
            String githubToken,
            Function<GHEventInfo, Condition> validator
    ) throws IOException {
        return countEvent(githubToken, validator, ghe -> {
            if (GHEvent.PUSH == ghe.getType()) {
                try {
                    Push push = ghe.getPayload(Push.class);
                    return push.getCommits().size();
                } catch (IOException e) {
                    return 0;
                }
            }
            return 0;
        });
    }

    public enum Condition {
        PASS, TARGET, STOP
    }
}
