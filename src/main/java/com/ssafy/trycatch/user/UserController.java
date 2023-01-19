package com.ssafy.trycatch.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/${apiPrefix}/user")
public class UserController {
	@GetMapping("/{userId}")
	public ResponseEntity<String> findUser(@PathVariable Long userId) {
		return ResponseEntity.ok("사용자를 조회합니다.");
	}

	@PatchMapping("/{userId}")
	public ResponseEntity<String> patchUser(@PathVariable Long userId) {
		return ResponseEntity.ok("사용자 정보를 수정합니다.");
	}

	@DeleteMapping("/{userId}")
	public ResponseEntity<String> removeUser(@PathVariable Long userId) {
		return ResponseEntity.ok("사용자가 탈퇴합니다. 단, 테이블에서는 활성 상태가 수정됩니다.");
	}

	@GetMapping("/{userId}/list")
	public ResponseEntity<String> findFollows(@PathVariable Long userId) {
		return ResponseEntity.ok("사용자의 팔로우/팔로워 목록을 조회합니다.");
	}

	@PutMapping("/{userId}/github/fetch")
	public ResponseEntity<String> fetchGitHub(@PathVariable Long userId) {
		return ResponseEntity.ok("GitHub 연동을 갱신합니다.");
	}

	@GetMapping("/{userId}/github")
	public ResponseEntity<String> findNodeId(@PathVariable Long userId) {
		return ResponseEntity.ok("사용자의 GitHub Node ID를 조회합니다.");
	}

	@PostMapping("/{userId}/tag")
	public ResponseEntity<String> addTag(@PathVariable Long userId) {
		return ResponseEntity.ok("사용자가 관심태그를 등록합니다.");
	}

	@DeleteMapping("/{userId}/tag/{tagId}")
	public ResponseEntity<String> removeTag(@PathVariable Long tagId) {
		return ResponseEntity.ok("사용자가 관심태그를 삭제합니다.");
	}

	@PutMapping("/ck")
	public ResponseEntity<String> exceptCK() {
		return ResponseEntity.ok("개발 상식 글을 피드에서 완전히 제외합니다.");
	}

	@GetMapping("/verification/{userId}")
	public ResponseEntity<String> isConfirmed(@PathVariable Long userId) {
		return ResponseEntity.ok("사용자의 기업 이메일 인증 여부를 확인합니다.");
	}

	@PostMapping("/verification/{userId}")
	public ResponseEntity<String> verifyCompany(@PathVariable Long userId) {
		return ResponseEntity.ok("회사 인증 이메일을 전송합니다.");
	}


	@PostMapping("/follow/{userId}")
	public ResponseEntity<String> followUser(@PathVariable Long userId) {
		return ResponseEntity.ok("다른 사용자를 팔로우합니다.");
	}

	@PutMapping("/follow/{userId}")
	public ResponseEntity<String> unfollowUser(@PathVariable Long userId) {
		return ResponseEntity.ok("다른 사용자를 언팔로우합니다.");
	}

	@GetMapping("/login")
	public ResponseEntity<String> login() {
		return ResponseEntity.ok("로그인합니다.");
	}
}

