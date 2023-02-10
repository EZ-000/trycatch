package com.ssafy.trycatch.common.domain;

public enum NotifyEnumType {
	FOLLOW(1L, "follow"),
	ANSWER_ACCEPTANCE(2L, "answerAcceptance"),
	ANSWER_REGISTRATION(3L, "answerRegistration");

	private final Long id;
	private final String type;

	NotifyEnumType(Long id, String type) {
		this.id = id;
		this.type = type;
	}

	public String getType() {
		return this.type;
	}

	public Long getId() {
		return this.id;
	}

	}