package com.ssafy.trycatch.user.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.trycatch.user.domain.Follow;
import com.ssafy.trycatch.user.domain.FollowRepository;
import com.ssafy.trycatch.user.domain.User;
import com.ssafy.trycatch.user.domain.UserRepository;
import com.ssafy.trycatch.user.service.exceptions.UserNotFoundException;

@Service
public class FollowService {

	private static final Logger log = LoggerFactory.getLogger(FollowService.class);

	private final FollowRepository followRepository;

	@Autowired
	public FollowService(FollowRepository followRepository) {
		this.followRepository = followRepository;
	}

	public Follow follow(User srcUser, User desUser) {
		return followRepository.save(Follow.builder()
			.follower(srcUser)
			.followee(desUser)
			.build());
	}
}