package com.ssafy.trycatch.common.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "notification")
public class Notification {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	/**
	 * 알림을 받을 유저를 의미함.
	 */
	@Column(name = "user_id")
	private Long userId;

	/**
	 * 팔로우라면 팔로우한 사용자의 ID
	 * 답변채택, 내글에 답변이 추가되는 경우, QuestionID
	 */
	@Column(name = "target_id")
	private Long targetId;

	@ToString.Exclude
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "typecode")
	private NotifyType type;

	@Column(name = "activated")
	private Boolean activated;

	@Column(name = "created_at")
	private LocalDateTime createdAt;

	@Size(max = 128)
	@Column(name = "title", length = 128)
	private String subject;

	@Builder
	public Notification(Long userId, Long targetId, NotifyType type, Boolean activated,
		LocalDateTime createdAt,
		String subject) {
		this.userId = userId;
		this.targetId = targetId;
		this.type = type;
		this.activated = activated;
		this.createdAt = createdAt;
		this.subject = subject;
	}
}