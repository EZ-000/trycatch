package com.ssafy.trycatch.user.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ssafy.trycatch.user.service.dto.GithubRepo;
import com.ssafy.trycatch.user.service.dto.GithubUser;
import lombok.extern.slf4j.Slf4j;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.graphql.client.GraphQlClient;
import org.springframework.graphql.client.HttpGraphQlClient;
import org.springframework.lang.Nullable;
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

    public static void main(String[] args) throws Exception {
        GithubService service = new GithubService();
        service.getVectorFromToken("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")
                .doOnSuccess(System.out::println)
                .block();
    }
}
