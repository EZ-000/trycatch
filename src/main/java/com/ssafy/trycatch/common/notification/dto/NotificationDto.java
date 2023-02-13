package com.ssafy.trycatch.common.notification.dto;

import static com.ssafy.trycatch.common.infra.config.ConstValues.*;

import com.ssafy.trycatch.common.domain.Notification;

import lombok.Builder;
import lombok.Data;

/**
 * id: number(알림id)
 * type : string(어떤 종류의 알림인지(follow, answerAcceptance, answerRegistration))
 * from: id(어디에서 발생한 데이터인지(팔로우라면 userId, 답변채택, 내글 답변 이라면 questionId))
 * title: string(유저이름 or 게시글 제목)
 * timestamp: (알림이 발생한 시점)
 */

@Data
public class NotificationDto {
	public final Long id;
	public final String type;
	public final Long from;
	public final String title;
	public final Long timestamp;

	@Builder
	public NotificationDto(Long id, String type, Long from, String title, Long timestamp) {
		this.id = id;
		this.type = type;
		this.from = from;
		this.title = title;
		this.timestamp = timestamp;
	}

	public static NotificationDto fromEntity(Notification notification) {
		return NotificationDto.builder()
			.id(notification.getId())
			.type(notification.getTypecode().getDescription())
			.from(notification.getTargetId())
			.title(notification.getTitle())
			.timestamp(notification.getCreatedAt().atZone(TZ_SEOUL)
				.toInstant()
				.toEpochMilli())
			.build();
	}
}
