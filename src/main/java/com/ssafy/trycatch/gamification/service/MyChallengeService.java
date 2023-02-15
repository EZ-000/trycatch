package com.ssafy.trycatch.gamification.service;

import com.ssafy.trycatch.common.service.CrudService;
import com.ssafy.trycatch.gamification.domain.MyChallenge;
import com.ssafy.trycatch.gamification.domain.MyChallengeRepository;
import com.ssafy.trycatch.gamification.domain.StatusInfo;
import com.ssafy.trycatch.qna.domain.Answer;
import com.ssafy.trycatch.qna.domain.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static com.ssafy.trycatch.gamification.domain.StatusInfo.FAIL;
import static com.ssafy.trycatch.gamification.domain.StatusInfo.SUCCESS;

@Service
public class MyChallengeService extends CrudService<MyChallenge, Long, MyChallengeRepository> {
    private final AnswerRepository answerRepository;

    @Autowired
    public MyChallengeService(
            MyChallengeRepository repository,
            AnswerRepository answerRepository) {
        super(repository);
        this.answerRepository = answerRepository;
    }

    public List<MyChallenge> findMyOngoingChallenges(Long userId, Pageable pageable) {
        return repository.findByUser_IdAndStatusInfo(userId, StatusInfo.ONGOING, pageable);
    }

    public MyChallenge findMyLatestChallenge(Long challengeId, Long userId) {
        return repository.findFirstByChallenge_IdAndUser_IdOrderByIdDesc(challengeId, userId)
                .orElse(new MyChallenge());
    }

    // challengeId에 맞는 조건 충족 여부를 확인하는 하드코딩 -> progress, state, earnedAt을 업데이트
    public void updateMyChallenge(Long userId, MyChallenge myChallenge) {

        final Long challengeId = myChallenge.getChallenge().getId();

        if (1 == challengeId) {     // 7일 7회 답변 챌린지
            challenge_1(userId, myChallenge);
        } else if (2 == challengeId) {
            challenge_2(userId, myChallenge);
        }
    }


    /**
     * 7일 7회 답변 챌린지:
     * 시작일부터 7일 동안 하루에 한 개 이상 연속으로 질문 작성 시 성공
     * @param userId 유저 아이디
     * @param myChallenge 나의 챌린지
     */
    private void challenge_1(Long userId, MyChallenge myChallenge) {
        final LocalDateTime startFrom = myChallenge.getStartFrom();
        final LocalDate endAt = myChallenge.getEndAt().toLocalDate();

        // findByCreatedAtGreaterThanEqual: 시작일자 이후로 작성한 답변을 가져오는 메서드
        List<Answer> answers =  answerRepository
                .findByUser_IdAndCreatedAtGreaterThanEqual(userId, startFrom);
        // 시작일자 이후로 작성한 답변을 순회하면서 일주일 날짜를 visited 체크
        HashMap<LocalDate, Boolean> visited = new HashMap<>();
        for (Answer answer : answers) {
            final Long questionUserId = answer.getQuestion().getUser().getId();
            if (!Objects.equals(userId, questionUserId)) {
                final LocalDate createdAt = answer.getCreatedAt().toLocalDate();
                visited.put(createdAt, true);
            }
        }
        // visited에 따라 progress 변경
        final Long newProgress = Math.round(visited.size() / 7.0 * 100.0);
        myChallenge.setProgress(newProgress);
        repository.save(myChallenge);
        // 100 == progress 시 statusInfo = success, earnedAt = curdate
        if (100.0 == myChallenge.getProgress()) {
            myChallenge.setStatusInfo(SUCCESS);
            myChallenge.setEarnedAt(LocalDateTime.now());
        }
        // 오늘 날짜가 endAt보다 크고, progress가 100이 아니면 state = fail
        if (LocalDate.now().isAfter(endAt) & myChallenge.getProgress() != 100.0) {
            myChallenge.setStatusInfo(FAIL);
        }
        repository.save(myChallenge);
    }

    /**
     * 한 달 10회 답변 챌린지:
     * @param userId 유저 아이디
     * @param myChallenge 나의 챌린지
     */
    private void challenge_2(Long userId, MyChallenge myChallenge) {
        final LocalDateTime startFrom = myChallenge.getStartFrom();
        final LocalDate endAt = myChallenge.getEndAt().toLocalDate();

        // findByCreatedAtGreaterThanEqual: 시작일자 이후로 작성한 답변을 가져오는 메서드
        List<Answer> allAnswers =  answerRepository
                .findByUser_IdAndCreatedAtGreaterThanEqual(userId, startFrom);

        final List<Answer> answers = new ArrayList<>();

        for (Answer a : allAnswers) {
            final Long questionUserId = a.getQuestion().getUser().getId();
            if (!Objects.equals(userId, questionUserId)) {
                answers.add(a);
            }
        }

        // 시작일자 이후로 작성한 답변의 개수에 따라 progress 변경
        final Long newProgress = Math.round(answers.size() / 10.0 * 100.0);
        myChallenge.setProgress(newProgress);
        repository.save(myChallenge);

        // 100 == progress 시 state = success, earnedAt = curdate
        if (100.0 == myChallenge.getProgress()) {
            myChallenge.setStatusInfo(SUCCESS);
            myChallenge.setEarnedAt(LocalDateTime.now());
        }

        // 오늘 날짜가 endAt보다 크고, progress가 100이 아니면 state = fail
        if (LocalDate.now().isAfter(endAt) & myChallenge.getProgress() != 100.0) {
            myChallenge.setStatusInfo(FAIL);
        }
        repository.save(myChallenge);
    }
}
