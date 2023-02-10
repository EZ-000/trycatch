package com.ssafy.trycatch.common.notification;

import static com.ssafy.trycatch.common.notification.NotificationController.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.ssafy.trycatch.common.domain.Notification;
import com.ssafy.trycatch.common.domain.NotificationRepository;
import com.ssafy.trycatch.common.domain.NotifyEnumType;
import com.ssafy.trycatch.common.domain.NotifyType;
import com.ssafy.trycatch.common.domain.NotifyTypeRepository;
import com.ssafy.trycatch.common.notification.dto.NotificationDto;
import com.ssafy.trycatch.qna.domain.Question;
import com.ssafy.trycatch.user.domain.User;
import com.ssafy.trycatch.user.domain.UserRepository;
import com.ssafy.trycatch.user.service.exceptions.UserNotFoundException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class NotificationService {
	private final String MESSAGE = "message";
	private final NotificationRepository notificationRepository;
	private final NotifyTypeRepository notifyTypeRepository;
	private final UserRepository userRepository;

	@Autowired
	public NotificationService(
		NotificationRepository notificationRepository,
		NotifyTypeRepository notifyTypeRepository,
		UserRepository userRepository) {
		this.notificationRepository = notificationRepository;
		this.notifyTypeRepository = notifyTypeRepository;
		this.userRepository = userRepository;
	}

	/**
	 * des 유저에게 알림을 푸시.
	 * src 유저의 행동으로 인해 des 유저에게 알림이 간다.
	 * @param src 주체
	 * @param des 대상
	 */
	public void notifyAddFollow(User src, Long des) {
		User toUser = userRepository.findById(des)
			.orElseThrow(UserNotFoundException::new);

		NotifyType notifyType = notifyTypeRepository
			.findById(NotifyEnumType.FOLLOW.getId())
			.orElse(NotifyType.builder().build());

		Notification notification = notificationRepository.save(dataBuilder(src, toUser, notifyType));
		emitOrSaveMessage(toUser, notification);
	}

	public void notifyAddAnswer(Question question) {
		User toUser = question.getUser();

		NotifyType notifyType = notifyTypeRepository
			.findById(NotifyEnumType.ANSWER_REGISTRATION.getId())
			.orElse(NotifyType.builder().build());

		Notification notification = notificationRepository.save(dataBuilder(question, notifyType));
		emitOrSaveMessage(toUser, notification);
	}

	public void notifyAcceptAnswer(Question question) {
		User toUser = question.getUser();

		NotifyType notifyType = notifyTypeRepository
			.findById(NotifyEnumType.ANSWER_ACCEPTANCE.getId())
			.orElse(NotifyType.builder().build());

		Notification notification = notificationRepository.save(dataBuilder(question, notifyType));
		emitOrSaveMessage(toUser, notification);
	}

	private Notification dataBuilder(Question question, NotifyType notifyType) {
		return Notification.builder()
			.userId(question.getUser().getId())
			.targetId(question.getId())
			.typecode(notifyType)
			.createdAt(LocalDateTime.now())
			.activated(true)
			.title(question.getTitle())
			.build();
	}

	private Notification dataBuilder(
		User fromUser,
		User toUser,
		NotifyType notifyType) {
		return Notification.builder()
			.userId(toUser.getId())
			.targetId(fromUser.getId())
			.typecode(notifyType)
			.createdAt(LocalDateTime.now())
			.activated(true)
			.title(fromUser.getUsername())
			.build();
	}

	private void emitOrSaveMessage(User toUser, Notification notification) {
		if (sseEmitters.containsKey(toUser.getId())) {
			SseEmitter sseEmitter = sseEmitters.get(toUser.getId());
			try {
				send(sseEmitter, MESSAGE, NotificationDto.fromEntity(notification));
			} catch (Exception e) {
				sseEmitters.remove(toUser.getId());
			}
			notification.setActivated(false);
		} else {
			// notificationRepository.save(notification);
		}
	}

	public void send(SseEmitter sseEmitter, String eventName, Object message) throws IOException {
		sseEmitter.send(SseEmitter.event()
			.name(eventName)
			.data(message));
	}

	public void sendSaved(SseEmitter sseEmitter, Long userId) {
		List<Notification> savedNotifylist = notificationRepository.findByUserIdOrderByIdAsc(userId);
		try {
			for (Notification notification : savedNotifylist) {
				if(notification.getActivated()) {
					send(sseEmitter, MESSAGE, NotificationDto.fromEntity(notification));
					notification.setActivated(false);
				}
			}
		} catch (IOException e) {
			log.info(e.getMessage());
		}
	}
}