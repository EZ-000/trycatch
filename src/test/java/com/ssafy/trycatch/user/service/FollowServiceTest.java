package com.ssafy.trycatch.user.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ssafy.trycatch.user.domain.FollowRepository;
import com.ssafy.trycatch.user.domain.User;

@SpringBootTest
class FollowServiceTest {
    @Autowired
    private FollowService followService;

    @Autowired
    private UserService userService;

    @Autowired
    private FollowRepository followRepository;

    @Test
    @DisplayName("--- register follow ---")
    public void enrollFollow() {
        User src = User.builder().id(1L).build();
        User des = User.builder().id(2L).build();

        Long prevCount = followRepository.count();
        followService.follow(src, des);

        Long postCount = followRepository.count();

        assertEquals(prevCount + 1, postCount);
    }
}