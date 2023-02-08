package com.ssafy.trycatch.common.sse;

import static com.ssafy.trycatch.common.sse.SseController.*;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.ssafy.trycatch.common.domain.Notification;
import com.ssafy.trycatch.common.domain.NotificationRepository;
import com.ssafy.trycatch.user.controller.dto.UserSubscriptionDto;
import com.ssafy.trycatch.user.domain.User;
import com.ssafy.trycatch.user.domain.UserRepository;

@Service
public class NotificationService {

	private final NotificationRepository notificationRepository;
	private final UserRepository userRepository;

	@Autowired
	public NotificationService(
		NotificationRepository notificationRepository,
		UserRepository userRepository) {
		this.notificationRepository = notificationRepository;
		this.userRepository = userRepository;
	}

	public void notifyAddFollow(User requestUser, Long targetId) {
		User targetUser = userRepository.findById(targetId).get();
		String alarm = requestUser.getUsername() + "님이 Follow 했습니다.";

		if (sseEmitters.containsKey(targetUser.getId())) {
			SseEmitter sseEmitter = sseEmitters.get(targetUser.getId());
			try {
				send(sseEmitter, "addFollow", alarm);
			} catch (Exception e) {
				sseEmitters.remove(targetUser.getId());
			}
		} else {
			StringBuilder sb = new StringBuilder();
			sb.append("{")
				.append("message:")
				.append(requestUser.getId())
				.append("님이 Follow 했습니다.")
				.append("}");

			Notification notification = Notification.builder()
				.userId(targetId)
				.message(sb.toString())
				.build();
			//notificationRepository.save(notification);
		}
	}

	public void notifyTest(Long id) {
		if (sseEmitters.containsKey(id)) {
			SseEmitter sseEmitter = sseEmitters.get(id);
			try {
				String alarm = id + "님이 event ";
				UserSubscriptionDto userSubscriptionDto = UserSubscriptionDto.builder()
					.companyId(1L)
					.companyName("test")
					.isSubscribe(false).build();

				send(sseEmitter, "addTest", "{\"companyId\":1,\"companyName\":\"test\",\"isSubscribe\":false}");
			} catch (Exception e) {
				sseEmitters.remove(id);
			}
		}
	}

	public void send(SseEmitter sseEmitter, String eventName, Object message) throws IOException {
		sseEmitter.send(SseEmitter.event()
			.name(eventName)
			.data(message));
	}
}