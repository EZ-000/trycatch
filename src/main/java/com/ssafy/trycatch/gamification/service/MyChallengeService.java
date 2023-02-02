package com.ssafy.trycatch.gamification.service;

import com.ssafy.trycatch.common.service.CrudService;
import com.ssafy.trycatch.gamification.domain.MyChallenge;
import com.ssafy.trycatch.gamification.domain.MyChallengeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MyChallengeService extends CrudService<MyChallenge, Long, MyChallengeRepository> {

    @Autowired
    public MyChallengeService(MyChallengeRepository repository) {
        super(repository);
    }
}
