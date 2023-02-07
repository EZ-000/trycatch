package com.ssafy.trycatch.user.controller;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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

import com.ssafy.trycatch.common.annotation.AuthUserElseGuest;
import com.ssafy.trycatch.qna.service.QuestionService;
import com.ssafy.trycatch.user.controller.dto.SimpleUserInfo;
import com.ssafy.trycatch.user.controller.dto.UserAnswerDto;
import com.ssafy.trycatch.user.controller.dto.UserDto;
import com.ssafy.trycatch.user.controller.dto.UserModifyDto;
import com.ssafy.trycatch.user.controller.dto.UserQuestionDto;
import com.ssafy.trycatch.user.controller.dto.UserRecentFeedDto;
import com.ssafy.trycatch.user.controller.dto.UserSubscriptionDto;
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
	private static final Long UN_LOGINED_USER = -1L;
	private final UserService userService;
	private final QuestionService questionService;

	@Autowired
	public UserController(
		UserService userService, QuestionService questionService
	) {
		this.userService = userService;
		this.questionService = questionService;
	}

	@GetMapping("/name")
	public ResponseEntity<String> findNameById(@AuthUserElseGuest User requestUser) {
		return ResponseEntity.ok(requestUser.getUsername());
	}

	@GetMapping("/id/{userName}")
	public ResponseEntity<Long> findUserId(@PathVariable String userName) {
		try {
			final Long userId = userService.findUserByName(userName)
				.getId();
			return ResponseEntity.ok(userId);
		} catch (UserNotFoundException u) {
			return ResponseEntity.badRequest()
				.build();
		}
	}

	@GetMapping("/image/{userId}")
	public ResponseEntity<String> findUserImage(@PathVariable Long userId) {
		try {
			final String img = userService.findUserById(userId)
				.getImageSrc();
			return ResponseEntity.ok(img);
		} catch (UserNotFoundException u) {
			return ResponseEntity.badRequest()
				.build();
		}
	}

	@GetMapping("/detail/{targetId}")
	public ResponseEntity<UserDto> findUser(
		@PathVariable Long targetId, @AuthUserElseGuest User requestUser
	) {
		if (UN_LOGINED_USER == requestUser.getId()) {
			return ResponseEntity.badRequest().build();
		}

		try {
			final User saved = userService.getDetailUserInfo(targetId);
			// tag List 를 들고오는 코드가 필요하다. 추가 하고 주석 삭제하자.
			final UserDto result = UserDto.from(saved, Collections.emptyList());

			return ResponseEntity.ok(result);
		} catch (UserNotFoundException u) {
			return ResponseEntity.badRequest()
				.build();
		}
	}

	@PatchMapping("/detail")
	public ResponseEntity<String> patchUser(
		@RequestBody UserModifyDto modifyDto, @AuthUserElseGuest User requestUser
	) {
		try {
			userService.modifyUser(requestUser.getId(), modifyDto);
			return ResponseEntity.ok("사용자 정보를 수정합니다.");
		} catch (UserNotFoundException u) {
			return ResponseEntity.badRequest()
				.build();
		}
	}

	@DeleteMapping("/detail/{targetId}")
	public ResponseEntity<String> removeUser(
		@PathVariable Long targetId,
		@AuthUserElseGuest User requestUser,
		@RequestBody WithdrawalRequestDto reason
	) {
		try {
			userService.inActivateUser(requestUser.getId(), reason.toEntity());
			return ResponseEntity.ok("사용자가 탈퇴합니다. 단, 테이블에서는 활성 상태가 수정됩니다.");
		} catch (UserNotFoundException u) {
			return ResponseEntity.badRequest()
				.build();
		}
	}

	@GetMapping("/{uid}/list")
	public ResponseEntity<List<SimpleUserInfo>> findFollows(
		@PathVariable Long uid, @RequestParam("type") String type
	) {
		try {
			final List<SimpleUserInfo> resultList = userService.findFollowList(uid, type)
				.stream()
				.map(SimpleUserInfo::from)
				.collect(Collectors.toList());
			return ResponseEntity.ok(resultList);
		} catch (UserNotFoundException | TypeNotPresentException u) {
			return ResponseEntity.badRequest()
				.build();
		}
	}

	@PostMapping("/follow/{uid}")
	public ResponseEntity<String> followUser(
		@PathVariable Long uid, @AuthUserElseGuest User requestUser
	) {
		try {
			// follow userId가 uid 를 팔로우 한다.
			userService.follow(requestUser.getId(), uid);
			return ResponseEntity.ok()
				.build();
		} catch (UserNotFoundException u) {
			return ResponseEntity.badRequest()
				.build();
		}
	}

	@PutMapping("/follow/{uid}")
	public ResponseEntity<String> unfollowUser(
		@PathVariable Long uid, @AuthUserElseGuest User requestUser
	) {
		try {
			// unfollow userId가 uid 를 팔로우 취소한다.
			userService.unfollow(requestUser.getId(), uid);
			return ResponseEntity.ok()
				.build();
		} catch (UserNotFoundException | AlreadyExistException u) {
			return ResponseEntity.badRequest()
				.build();
		}
	}

	@GetMapping("/verification/{uid}")
	public ResponseEntity<VerifyDto> isConfirmed(@PathVariable Long uid) {
		try {
			Boolean result = userService.getVerification(uid);
			return ResponseEntity.ok(VerifyDto.from(result));
		} catch (UserNotFoundException u) {
			return ResponseEntity.badRequest()
				.build();
		}
	}

	@PostMapping("/verification/{uid}")
	public ResponseEntity<String> verifyCompany(
		@PathVariable Long uid, @AuthUserElseGuest User requestUser
	) {
		return ResponseEntity.ok("회사 인증 이메일을 전송합니다.");
	}

	@GetMapping("/{userName}/github/fetch")
	public ResponseEntity<String> fetchGitHub(
		Authentication authentication, @PathVariable String userName
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
	public ResponseEntity<String> removeTag(
		@PathVariable String userName,
		@PathVariable Long tagId
	) {
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

	//	---------------------
	// 	*   User Activity   *
	// 	---------------------

	@GetMapping("/{uid}/answer/list")
	public ResponseEntity<List<UserAnswerDto>> findUserAnswers(
		@PathVariable Long uid, @AuthUserElseGuest User requestUser) {
		// Common Logic
		if (!userService.isExist(uid)) {
			return ResponseEntity.badRequest()
				.build();
		}

		try {
			List<UserAnswerDto> result = userService.getUserAnswerDtoList(uid, requestUser.getId());
			return ResponseEntity.ok(result);
		} catch (UserNotFoundException u) {
			return ResponseEntity.badRequest().build();
		}
	}

	@GetMapping("/{uid}/question/list")
	public ResponseEntity<List<UserQuestionDto>> findUserQuestions(
		@PathVariable Long uid, @AuthUserElseGuest User requestUser) {
		// Common Logic
		if (!userService.isExist(uid)) {
			return ResponseEntity.badRequest()
				.build();
		}

		try {
			List<UserQuestionDto> result = userService.getUserQuestionDtoList(uid, requestUser.getId());
			return ResponseEntity.ok(result);
		} catch (UserNotFoundException u) {
			return ResponseEntity.badRequest().build();
		}
	}

	@GetMapping("/{userId}/recent/list")
	public ResponseEntity<List<UserRecentFeedDto>> findRecentFeed(
		@PathVariable Long uid, @AuthUserElseGuest User requestUser) {
		if (UN_LOGINED_USER == requestUser.getId()) {
			return ResponseEntity.badRequest().build();
		}

		List<UserRecentFeedDto> result = userService.findRecentFeedList(requestUser.getId());
		return ResponseEntity.ok(result);
	}

	@GetMapping("/{userId}/subscription/list")
	public ResponseEntity<List<UserSubscriptionDto>> findSubscriptionList(
		@PathVariable Long userId, @AuthUserElseGuest User requestUser) {
		if (UN_LOGINED_USER == requestUser.getId()) {
			return ResponseEntity.badRequest().build();
		}
		try {
			List<UserSubscriptionDto> result = userService.findSubscriptionList(userId,requestUser.getId());

			return ResponseEntity.ok(result);
		} catch (UserNotFoundException u) {
			return ResponseEntity.badRequest().build();
		}
	}

	@GetMapping("/{userId}/history")
	public ResponseEntity<String> findHistory(@PathVariable Long userId) {
		return ResponseEntity.ok("사용자의 시간에 따른 레포지토리 분석 결과를 조회합니다.");
	}

	@GetMapping("/{userId}/badge/list")
	public ResponseEntity<String> findBadge(@PathVariable Long userId) {
		return ResponseEntity.ok("획득한 (대표) 배지 리스트를 조회합니다.");
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

	@GetMapping("/qna/rank")
	public ResponseEntity<String> findRanks() {
		return ResponseEntity.ok("질문답변 활동에서 가장 높은 포인트를 얻은 사람 목록을 조회합니다.");
	}

}

