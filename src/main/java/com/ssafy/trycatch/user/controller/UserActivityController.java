package com.ssafy.trycatch.user.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/${apiPrefix}/user-activity")
public class UserActivityController {
	@GetMapping("/{userId}/answer/list")
	public ResponseEntity<String> findUserAnswers(@PathVariable Long userId) {
		log.error(this.getClass().getName(), "findUserAnswers");
		return ResponseEntity.ok("사용자가 작성한 답변 리스트를 조회합니다.");
	}

	@GetMapping("/{userId}/question/list")
	public ResponseEntity<String> findUserQuestions(@PathVariable Long userId) {
		return ResponseEntity.ok("사용자가 작성한 질문 리스트를 조회합니다.");
	}

	@GetMapping("/{userId}/history")
	public ResponseEntity<String> findHistory(@PathVariable Long userId) {
		return ResponseEntity.ok("사용자의 시간에 따른 레포지토리 분석 결과를 조회합니다.");
	}

	@GetMapping("/{userId}/badge/list")
	public ResponseEntity<String> findBadge(@PathVariable Long userId) {
		return ResponseEntity.ok("획득한 (대표) 배지 리스트를 조회합니다.");
	}

	@GetMapping("/{userId}/recent/list")
	public ResponseEntity<String> findRecentFeed(@PathVariable Long userId) {
		return ResponseEntity.ok("사용자가 최근 본 피드를 조회합니다.");
	}

	@GetMapping("/{userId}/subscription/list")
	public ResponseEntity<String> findSubscriptionList(@PathVariable Long userId) {
		return ResponseEntity.ok("사용자가 구독하고 있는 기업 블로그 포스트 리스트를 조회합니다.");
	}

	@PostMapping("/news")
	public ResponseEntity<String> subscribeNewsletter() {
		return ResponseEntity.ok("뉴스레터 받기를 등록합니다.");
	}

	@PutMapping("/news")
	public ResponseEntity<String> unsubscribeNewsletter() {
		return ResponseEntity.ok("뉴스레터 받기를 취소합니다.");
	}

	@PostMapping("/report")
	public ResponseEntity<String> report() {
		return ResponseEntity.ok("사람, 게시글, 답변, 피드 등을 신고합니다.");
	}

}
