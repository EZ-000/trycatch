package com.ssafy.trycatch.common.sse;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.ssafy.trycatch.common.infra.config.jwt.TokenService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/${apiPrefix}")
public class SseController {

	public static Map<Long, SseEmitter> sseEmitters = new ConcurrentHashMap<>();
	private final TokenService tokenService;
	private final NotificationService notificationService;

	@Autowired
	public SseController(
		TokenService tokenService,
		NotificationService notificationService) {
		this.tokenService = tokenService;
		this.notificationService = notificationService;
	}

	@GetMapping(value = "/connect", consumes = MediaType.ALL_VALUE)
	public SseEmitter subscribe(@RequestParam String token) {

		// 토큰에서 userId값 확인
		Long userId = Long.parseLong(tokenService.getUid(token));

		// SseEmitter 생성
		SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);
		try {
			notificationService.send(sseEmitter, "connect", "dummy");
			// db에 쌓인것이 있다면 쭉 send
			//notificationService.prevSend


			// 없다면 nothing
		} catch (IOException e) {
			e.printStackTrace();
		}

		// userId key값으로 해서 SseEmitter를 저장
		sseEmitters.put(userId, sseEmitter);

		sseEmitter.onCompletion(() -> sseEmitters.remove(userId));
		sseEmitter.onTimeout(() -> sseEmitters.remove(userId));
		sseEmitter.onError((e) -> sseEmitters.remove(userId));

		return sseEmitter;
	}
}