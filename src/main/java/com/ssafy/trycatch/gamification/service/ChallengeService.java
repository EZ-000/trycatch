package com.ssafy.trycatch.gamification.service;

import com.ssafy.trycatch.common.service.CrudService;
import com.ssafy.trycatch.gamification.domain.Challenge;
import com.ssafy.trycatch.gamification.domain.ChallengeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChallengeService extends CrudService<Challenge, Long, ChallengeRepository> {

    @Autowired
    public ChallengeService(ChallengeRepository repository) {
        super(repository);
    }


}
