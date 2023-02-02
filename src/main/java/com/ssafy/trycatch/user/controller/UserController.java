package com.ssafy.trycatch.user.controller;

import java.util.Collections;
import java.util.Optional;

import com.ssafy.trycatch.common.infra.config.jwt.TokenService;
import com.ssafy.trycatch.user.controller.dto.UserDto;
import com.ssafy.trycatch.user.controller.dto.WithdrawalRequestDto;
import com.ssafy.trycatch.user.domain.Follow;
import com.ssafy.trycatch.user.domain.User;
import com.ssafy.trycatch.user.service.FollowService;
import com.ssafy.trycatch.user.service.UserService;
import com.ssafy.trycatch.user.service.WithdrawalService;

import lombok.extern.slf4j.Slf4j;

import org.apache.kafka.common.security.oauthbearer.secured.ValidateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;

@Slf4j
@RestController
@RequestMapping("/${apiPrefix}/user")
public class UserController {

	private final UserService userService;
	private final TokenService tokenService;
	private final FollowService followService;
	private final WithdrawalService withdrawalService;

	@Autowired
	public UserController(UserService userService,
		TokenService tokenService,
		FollowService followService,
		WithdrawalService withdrawalService) {
		this.userService = userService;
		this.tokenService = tokenService;
		this.followService = followService;
		this.withdrawalService = withdrawalService;
	}

	@GetMapping("/{userName}")
	public ResponseEntity<UserDto> findUser(
		@PathVariable String userName,
		@Nullable @AuthenticationPrincipal Long userId) {
		if(null == userId)
			return ResponseEntity.badRequest().build();

		final User saved = userService.getDetailUserInfo(userId, userName);
		// tag List를 들고오는 코드가 필요하다. 추가 하고 주석 삭제하자.
		final UserDto result = UserDto.from(saved, Collections.emptyList());
		return ResponseEntity.ok(result);
	}

	@PatchMapping("/{userName}")
	public ResponseEntity<String> patchUser(@PathVariable String userName) {
		return ResponseEntity.ok("사용자 정보를 수정합니다.");
	}

	@DeleteMapping("/{userName}")
	public ResponseEntity<String> removeUser(@PathVariable String userName,
		@RequestHeader(value = "Authorization", defaultValue = "NONE") String token,
		@RequestBody WithdrawalRequestDto reason) {
		if (!tokenService.verifyToken(token)) {
			throw new ValidateException("Invalid Token");
		}
		userService.inActivateUser(Long.parseLong(tokenService.getAccessToken(token)));
		withdrawalService.registerReason(reason);
		return ResponseEntity.ok("사용자가 탈퇴합니다. 단, 테이블에서는 활성 상태가 수정됩니다.");
	}

	@GetMapping("/{userName}/list")
	public ResponseEntity<String> findFollows(@PathVariable String userName) {
		return ResponseEntity.ok("사용자의 팔로우/팔로워 목록을 조회합니다.");
	}

	@PutMapping("/{userName}/github/fetch")
	public ResponseEntity<String> fetchGitHub(@PathVariable String userName) {
		return ResponseEntity.ok("GitHub 연동을 갱신합니다.");
	}

	@GetMapping("/{userName}/github")
	public ResponseEntity<String> findNodeId(@PathVariable String userName) {
		return ResponseEntity.ok("사용자의 GitHub Node ID를 조회합니다.");
	}

	@PostMapping("/{userName}/tag")
	public ResponseEntity<String> addTag(@PathVariable String userName) {
		return ResponseEntity.ok("사용자가 관심태그를 등록합니다.");
	}

	@DeleteMapping("/{userName}/tag/{tagId}")
	public ResponseEntity<String> removeTag(@PathVariable String userName) {
		return ResponseEntity.ok("사용자가 관심태그를 삭제합니다.");
	}

	@PutMapping("/ck")
	public ResponseEntity<String> exceptCK() {
		return ResponseEntity.ok("개발 상식 글을 피드에서 완전히 제외합니다.");
	}

	@GetMapping("/verification/{userName}")
	public ResponseEntity<String> isConfirmed(@PathVariable String userName) {
		return ResponseEntity.ok("사용자의 기업 이메일 인증 여부를 확인합니다.");
	}

	@PostMapping("/verification/{userName}")
	public ResponseEntity<String> verifyCompany(@PathVariable String userName) {
		return ResponseEntity.ok("회사 인증 이메일을 전송합니다.");
	}

	@PostMapping("/follow/{userName}")
	public ResponseEntity<String> followUser(@PathVariable String userName,
		@RequestHeader(value = "Authorization", defaultValue = "NONE") String token) {
		if (!tokenService.verifyToken(token)) {
			throw new ValidateException("Invalid Token");
		}

		//	follow(src,des), src가 des를 팔로우.
		// final User src = userService.findUserById(Long.parseLong(tokenService.getUid(token)));
		// final User des = userService.findUserById(userId);
		// final Follow follow = followService.follow(src, des);

		return ResponseEntity.ok("다른 사용자를 팔로우합니다.");
	}

	@PutMapping("/follow/{userName}")
	public ResponseEntity<String> unfollowUser(@PathVariable String userName) {
		return ResponseEntity.ok("다른 사용자를 언팔로우합니다.");
	}

	@GetMapping("/login")
	public ResponseEntity<String> login(@PathParam("code") String code, HttpServletResponse response) {
		return ResponseEntity.ok("로그인성공");
	}
}

