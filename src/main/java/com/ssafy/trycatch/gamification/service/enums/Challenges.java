package com.ssafy.trycatch.gamification.service.enums;

import com.ssafy.trycatch.gamification.domain.MyChallenge;
import lombok.RequiredArgsConstructor;
import org.kohsuke.github.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.function.BiFunction;

@RequiredArgsConstructor
public enum Challenges {

    ALWAYS_ACHIEVE((myChallenge, githubToken) -> {
        // 항상 달성한다.
        return true;
    }),
    STUDY_AFTER_WORK((myChallenge, githubToken) -> {
        try {
            // 주중 당일 18시 ~ 당일 24시 까지 푸쉬 이력이 있어야 한다.
            GitHub gitHub = GitHub.connectUsingOAuth(githubToken);
            GHMyself myself = gitHub.getMyself();

            PagedIterator<GHEventInfo> ghEventInfos = myself.listEvents()._iterator(10);
            final LocalDateTime now = LocalDateTime.now();
            final LocalDateTime startFrom = myChallenge.getStartFrom();
            final LocalDateTime endAt = myChallenge.getEndAt();

            while (ghEventInfos.hasNext()) {
                for (GHEventInfo eventInfo : ghEventInfos.nextPage()) {

                    final long eventCreatedTimestamp = eventInfo.getCreatedAt().getTime();
                    final LocalDateTime eventCreatedAt
                            = LocalDateTime.ofEpochSecond(eventCreatedTimestamp, 0, ZoneOffset.ofHours(9));

                    // 이벤트 대상 일이 아닌 경우
                    if (!(startFrom.isAfter(eventCreatedAt) || endAt.isBefore(eventCreatedAt))) {
                        return false;
                    }

                    // 미션 완수 조건
                }
            }


        } catch (IOException e) {
            return false;
        }
        return null;
    });

    private final BiFunction<MyChallenge, String, Boolean> isAchieved;
}
