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

    /**
     * 특정 유저의 현재부터 특정 시점까지의 타겟 이벤트 발생 횟수를 카운트
     * @param githubToken 깃허브 토큰
     * @param from 날짜 경계값 ( 이 시간보다 이른 이벤트만 카운팅 )
     * @param events 카운트할 이벤트 종류
     */
    public Integer countEvents(String githubToken, Date from, GHEvent... events) throws IOException {
        final GitHub gitHub = GitHub.connectUsingOAuth(githubToken);
        final GHMyself myself = gitHub.getMyself();
        final Set<GHEvent> targetEvents = new HashSet<>(Arrays.asList(events));
        final PagedIterator<GHEventInfo> ghEventInfoPagedIterator = myself.listEvents()
                                                                          ._iterator(10);
        int count = 0;

        while (ghEventInfoPagedIterator.hasNext()) {
            for (GHEventInfo eventInfo : ghEventInfoPagedIterator.nextPage()) {

                // 특정 날짜보다 오래된 이벤트라면 현재 카운트를 반환
                if (eventInfo.getCreatedAt().after(from)) {
                    return count;
                }

                // 카운팅 대상 이벤트인경우 카운트
                if (targetEvents.contains(eventInfo.getType())) {
                    count++;
                }
            }
        }

        return count;
    }

    /**
     * 특정 유저의 현재부터 특정 시점까지 커밋 횟수를 카운트
     * @param githubToken 깃허브 토큰
     * @param from 날짜 경계값 ( 이 시간보다 이른 이벤트만 카운팅 )
     */
    public Integer countCommits(String githubToken, Date from) throws IOException {

        final GitHub gitHub = GitHub.connectUsingOAuth(githubToken);
        final GHMyself myself = gitHub.getMyself();
        final PagedIterator<GHEventInfo> ghEventInfoPagedIterator = myself.listEvents()
                ._iterator(10);

        int count = 0;
        while (ghEventInfoPagedIterator.hasNext()) {
            for (GHEventInfo eventInfo : ghEventInfoPagedIterator.nextPage()) {

                // 특정 날짜보다 오래된 이벤트라면 현재 카운트를 반환
                if (eventInfo.getCreatedAt().after(from)) {
                    return count;
                }

                // 카운팅 대상 이벤트인경우 카운트
                if (GHEvent.PUSH == eventInfo.getType()) {
                    Push push = eventInfo.getPayload(Push.class);
                    count += push.getCommits().size();
                }
            }
        }

        return count;
    }
}
