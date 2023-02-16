package com.ssafy.trycatch.common.notification;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.ssafy.trycatch.common.annotation.AuthUserElseGuest;
import com.ssafy.trycatch.common.infra.config.jwt.TokenService;
import com.ssafy.trycatch.user.domain.User;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/${apiPrefix}")
public class NotificationController {

	public static Map<Long, SseEmitter> sseEmitters = new ConcurrentHashMap<>();
	private final TokenService tokenService;
	private final NotificationService notificationService;

	@Autowired
	public NotificationController(
		TokenService tokenService,
		NotificationService notificationService) {
		this.tokenService = tokenService;
		this.notificationService = notificationService;
	}

	@GetMapping(value = "/connect", consumes = MediaType.TEXT_EVENT_STREAM_VALUE)
	public ResponseEntity<SseEmitter> subscribe(@RequestParam String token,
		HttpServletResponse response) {
		// 토큰에서 userId값 확인
		Long userId = Long.parseLong(tokenService.getUid(token));

		// SseEmitter 생성
		SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);

		// try {
		// 	notificationService.send(sseEmitter, "connect", "dummy");
		// } catch (IOException e) {
		// 	log.info(e.getMessage());
		// }

		// 임시처리, 누수 의심 disable 처리

		// userId key 값으로 해서 SseEmitter 를 저장
		sseEmitters.put(userId, sseEmitter);

		sseEmitter.onCompletion(() -> sseEmitters.remove(userId));
		sseEmitter.onTimeout(() -> sseEmitters.remove(userId));
		sseEmitter.onError((e) -> sseEmitters.remove(userId));

		response.setHeader("Connection","keep-alive");
		response.setHeader("Cache-Control","no-cache");
		response.setHeader("X-Accel-Buffering","no");

		return ResponseEntity.ok(sseEmitter);
	}

	@GetMapping("/notification")
	public ResponseEntity<String> savedNotification(
		@AuthUserElseGuest User requestUser
	) {
		final Long userId = requestUser.getId();
		SseEmitter sseEmitter = sseEmitters.get(userId);
		notificationService.sendSaved(sseEmitter, userId);
		return ResponseEntity.ok("");
	}

	@PutMapping("/notification")
	public ResponseEntity<String> readNotification(
		@RequestBody List<Long> userReadList,
		@AuthUserElseGuest User requestUser
	) {
		notificationService.readAlarm(userReadList, requestUser);
		return ResponseEntity.ok("");
	}
}
