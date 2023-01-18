package com.ssafy.trycatch.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/follow")
public class FollowController {
	@PostMapping("/{userId}")
	public String followUser(@PathVariable String userId) {
		return "다른 사용자를 팔로우합니다.";
	}

	@PutMapping("/{userId}")
	public String unfollowUser(@PathVariable String userId) {
		return "다른 사용자를 언팔로우합니다.";
	}
}
