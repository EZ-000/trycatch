package com.ssafy.trycatch.user.controller;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.trycatch.user.controller.dto.SimpleUserInfo;
import com.ssafy.trycatch.user.controller.dto.UserDto;
import com.ssafy.trycatch.user.controller.dto.UserModifytDto;
import com.ssafy.trycatch.user.controller.dto.VerifyDto;
import com.ssafy.trycatch.user.controller.dto.WithdrawalRequestDto;
import com.ssafy.trycatch.user.domain.User;
import com.ssafy.trycatch.user.service.UserService;
import com.ssafy.trycatch.user.service.exceptions.AlreadyExistException;
import com.ssafy.trycatch.user.service.exceptions.UserNotFoundException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/${apiPrefix}/user")
public class UserController {

	private final UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/name")
	public ResponseEntity<String> findNameById(
		@Nullable @AuthenticationPrincipal Long userId) {
		if (null == userId) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		final User user = userService.findUserById(userId);

		return ResponseEntity.ok(user.getUsername());
	}

	@GetMapping("/id/{userName}")
	public ResponseEntity<Long> findUserId(@PathVariable String userName) {
		try {
			final Long userId = userService.findUserByName(userName).getId();
			return ResponseEntity.ok(userId);
		} catch (UserNotFoundException u) {
			return ResponseEntity.badRequest().build();
		}
	}

	@GetMapping("/image/{userId}")
	public ResponseEntity<String> findUserImage(@PathVariable Long userId) {
		try {
			final String img = userService.findUserById(userId).getImageSrc();
			return ResponseEntity.ok(img);
		} catch (UserNotFoundException u) {
			return ResponseEntity.badRequest().build();
		}
	}

	@GetMapping("/detail/{targetId}")
	public ResponseEntity<UserDto> findUser(
		@PathVariable Long targetId,
		@Nullable @AuthenticationPrincipal Long userId) {
		if (null == userId) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		try {
			final User saved = userService.getDetailUserInfo(targetId);
			// tag List를 들고오는 코드가 필요하다. 추가 하고 주석 삭제하자.
			final UserDto result = UserDto.from(saved, Collections.emptyList());

			return ResponseEntity.ok(result);
		} catch (UserNotFoundException u) {
			return ResponseEntity.badRequest().build();
		}
	}

	@PatchMapping("/detail")
	public ResponseEntity<String> patchUser(
		@RequestBody UserModifytDto modifytDto,
		@Nullable @AuthenticationPrincipal Long userId) {
		if (null == userId) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		try {
			userService.modifyUser(userId, modifytDto);
			return ResponseEntity.ok("사용자 정보를 수정합니다.");
		} catch (UserNotFoundException u) {
			return ResponseEntity.badRequest().build();
		}
	}

	@DeleteMapping("/detail/{targetId}")
	public ResponseEntity<String> removeUser(@PathVariable Long targetId,
		@Nullable @AuthenticationPrincipal Long userId,
		@RequestBody WithdrawalRequestDto reason) {
		if (null == userId) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		try {
			userService.inActivateUser(userId, reason.toEntity());
			return ResponseEntity.ok("사용자가 탈퇴합니다. 단, 테이블에서는 활성 상태가 수정됩니다.");
		} catch (UserNotFoundException u) {
			return ResponseEntity.badRequest().build();
		}
	}

	@GetMapping("/{uid}/list")
	public ResponseEntity<List<SimpleUserInfo>> findFollows(
		@PathVariable Long uid,
		@RequestParam("type") String type) {
		try {
			final List<SimpleUserInfo> resultList = userService.findFollowList(uid, type)
				.stream()
				.map(e -> SimpleUserInfo.from(e))
				.collect(Collectors.toList());
			return ResponseEntity.ok(resultList);
		} catch (UserNotFoundException | TypeNotPresentException e) {
			return ResponseEntity.badRequest().build();
		}
	}

	@PostMapping("/follow/{uid}")
	public ResponseEntity<String> followUser(
		@PathVariable Long uid,
		@Nullable @AuthenticationPrincipal Long userId) {
		if (null == userId) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		try {
			// follow userId가 uid를 팔로우 한다.
			userService.follow(userId, uid);
			return ResponseEntity.ok().build();

		} catch (UserNotFoundException u) {
			return ResponseEntity.badRequest().build();
		}
	}

	@PutMapping("/follow/{uid}")
	public ResponseEntity<String> unfollowUser(
		@PathVariable Long uid,
		@Nullable @AuthenticationPrincipal Long userId) {
		if (null == userId) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		try {
			// unfollow userId가 uid를 팔로우 취소한다.
			userService.unfollow(userId, uid);
			return ResponseEntity.ok().build();

		} catch (UserNotFoundException | AlreadyExistException u) {
			return ResponseEntity.badRequest().build();
		}
	}

	@GetMapping("/verification/{uid}")
	public ResponseEntity<VerifyDto> isConfirmed(@PathVariable Long uid) {
		try {
			Boolean result = userService.getVerification(uid);
			return ResponseEntity.ok(VerifyDto.from(result));

		} catch (UserNotFoundException u) {
			return ResponseEntity.badRequest().build();
		}
	}

	@PostMapping("/verification/{uid}")
	public ResponseEntity<String> verifyCompany(
		@PathVariable Long uid,
		@Nullable @AuthenticationPrincipal Long userId) {

		return ResponseEntity.ok("회사 인증 이메일을 전송합니다.");
	}

	@GetMapping("/{userName}/github/fetch")
	public ResponseEntity<String> fetchGitHub(
			Authentication authentication,
			@PathVariable String userName
	) {
		final String oAuthToken = (String)authentication.getCredentials();
		System.out.println(oAuthToken);

		return ResponseEntity.ok("GitHub 연동을 갱신합니다.");
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

	@GetMapping("/login")
	public ResponseEntity<String> login(@PathParam("code") String code, HttpServletResponse response) {
		return ResponseEntity.ok("로그인성공");
	}
}

