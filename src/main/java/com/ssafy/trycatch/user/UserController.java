package com.ssafy.trycatch.user;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping()
public class UserController {
	@GetMapping("/verification/{userId}")
	public String isConfirmed(@PathVariable String userId) {
		return "사용자의 기업 이메일 인증 여부를 확인합니다.";
	}

	@PostMapping("/verification/{userId}")
	public String confirmCompany(@PathVariable String userId) {
		return "회사 인증 이메일을 전송합니다.";
	}

	@GetMapping("/user/{userId}")
	public String readUser(@PathVariable String userId) {
		return "사용자를 조회합니다.";
	}

	@PatchMapping("/user/{userId}")
	public String patchUser(@PathVariable String userId) {
		return "사용자 정보를 수정합니다.";
	}

	@DeleteMapping("/user/{userId}")
	public String deleteUser(@PathVariable String userId) {
		return "사용자가 탈퇴합니다. 단, 테이블에서는 활성 상태가 수정됩니다.";
	}

	@GetMapping("/user/{userId}/list")
	public String readFollow(@PathVariable String userId) {
		return "사용자의 팔로우/팔로워 목록을 조회합니다.";
	}

	@PutMapping("/user/{userId}/github/fetch")
	public String fetchGitHub(@PathVariable String userId) {
		return "GitHub 연동을 갱신합니다.";
	}

	@GetMapping("/user/{userId}/github")
	public String readNodeId(@PathVariable String userId) {
		return "사용자의 GitHub Node ID를 조회합니다.";
	}

	@GetMapping("/user/{userId}/answer/list")
	public String readUserAnswer(@PathVariable String userId) {
		return "사용자가 작성한 답변 리스트를 조회합니다.";
	}

	@GetMapping("/user/{userId}/question/list")
	public String readUserQuestion(@PathVariable String userId) {
		return "사용자가 작성한 질문 리스트를 조회합니다.";
	}

	@GetMapping("/user/{userId}/history")
	public String readHistory(@PathVariable String userId) {
		return "사용자의 시간에 따른 레포지토리 분석 결과를 조회합니다.";
	}

	@GetMapping("/user/{userId}/badge/list")
	public String readBadge(@PathVariable String userId) {
		return "획득한 (대표) 배지 리스트를 조회합니다.";
	}

	@GetMapping("/user/{userId}/recent/list")
	public String readRecentFeed(@PathVariable String userId) {
		return "사용자가 최근 본 피드를 조회합니다.";
	}

	@GetMapping("/user/{userId}/subscription/list")
	public String readSubscriptionList(@PathVariable String userId) {
		return "사용자가 구독하고 있는 기업 블로그 포스트 리스트를 조회합니다.";
	}

	@PostMapping("/user/{userId}/tag")
	public String createTag(@PathVariable String userId) {
		return "사용자가 관심태그를 등록합니다.";
	}

	@DeleteMapping("/user/{userId}/tag/{tagId}")
	public String deleteTage(@PathVariable String tagId) {
		return "사용자가 관심태그를 삭제합니다.";
	}

	@PostMapping("/follow/{userId}")
	public String followUser(@PathVariable String userId) {
		return "다른 사용자를 팔로우합니다.";
	}

	@PutMapping("/follow/{userId}")
	public String unfollowUser(@PathVariable String userId) {
		return "다른 사용자를 언팔로우합니다.";
	}

	@GetMapping("/auth/login")
	public String login() {
		return "로그인합니다.";
	}

	@PostMapping("/report")
	public String report() {
		return "사람, 게시글, 답변, 피드 등을 신고합니다.";
	}

	@PostMapping("/user/news")
	public String requestNewsletter() {
		return	"뉴스레터 받기를 등록합니다.";
	}

	@PutMapping("/user/news")
	public String stopNewsletter() {
		return	"뉴스레터 받기를 취소합니다.";
	}


}
