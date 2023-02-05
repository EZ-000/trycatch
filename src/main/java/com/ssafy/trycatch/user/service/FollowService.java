package com.ssafy.trycatch.user.service;

import com.ssafy.trycatch.common.service.CrudService;
import com.ssafy.trycatch.user.domain.Follow;
import com.ssafy.trycatch.user.domain.FollowRepository;
import com.ssafy.trycatch.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FollowService extends CrudService<Follow, Long, FollowRepository> {

    @Autowired
    public FollowService(FollowRepository followRepository) {
        super(followRepository);
    }

    public Follow follow(User srcUser, User desUser) {
        return repository.save(Follow.builder().follower(srcUser).followee(desUser).build());
    }

    public Boolean isFollowByFollowerAndFollowee(Long followerId, Long followeeId) {
        return repository.existsByFollowerIdAndFolloweeId(followerId, followeeId);
    }
}