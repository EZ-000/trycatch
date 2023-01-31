package com.ssafy.trycatch.user.controller.dto;

import com.ssafy.trycatch.common.domain.Report;
import com.ssafy.trycatch.user.domain.Withdrawal;

public class WithdrawalRequestDto {
	private Long userId;
	private String content;

	public Withdrawal newWithdrawal() {
		return Withdrawal.builder()
			.id(userId)
			.reason(content)
			.build();
	}
}
