package com.ssafy.trycatch.gamification.service;

import com.ssafy.trycatch.common.service.CrudService;
import com.ssafy.trycatch.gamification.domain.MyChallenge;
import com.ssafy.trycatch.gamification.domain.MyChallengeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyChallengeService extends CrudService<MyChallenge, Long, MyChallengeRepository> {

    @Autowired
    public MyChallengeService(MyChallengeRepository repository) {
        super(repository);
    }

    public Boolean IsJoined(Long challengeId, Long userId) {
        return repository.existsByChallenge_IdAndUser_Id(challengeId, userId);
    }

    public Boolean IsSucceed(Long challengeId, Long userId) {
        final MyChallenge myChallenge = repository
                .findByChallenge_IdAndUser_Id(challengeId, userId)
                .orElse(null);

        final Boolean isSucceed;
        if (null == myChallenge) {
            isSucceed = false;
        } else {
            isSucceed = myChallenge.getSucceed();
        }
        return isSucceed;
    }

    public Long getProgress(Long challengeId, Long userId) {
        final MyChallenge myChallenge = repository
                .findByChallenge_IdAndUser_Id(challengeId, userId)
                .orElse(null);

        final Long progress;
        if (null == myChallenge) {
            progress = 0L;
        } else {
            progress = myChallenge.getProgress();
        }
        return progress;
    }

    public List<MyChallenge> findMyChallenges(Long userId, Pageable pageable) {
        return repository.findByUser_IdOrderByStartFromDesc(userId, pageable);
    }
}
