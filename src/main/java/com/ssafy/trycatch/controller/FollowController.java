package com.ssafy.trycatch.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/${apiPrefix}/follow")
public class FollowController {
	@PostMapping("/{userId}")
	public ResponseEntity<String> followUser(@PathVariable String userId) {
		return ResponseEntity.ok("다른 사용자를 팔로우합니다.");
	}

	@PutMapping("/{userId}")
	public ResponseEntity<String> unfollowUser(@PathVariable String userId) {
		return ResponseEntity.ok("다른 사용자를 언팔로우합니다.");
	}
}
