package com.ssafy.trycatch.user.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ssafy.trycatch.user.controller.dto.WithdrawalRequestDto;
import com.ssafy.trycatch.user.domain.WithdrawalRepository;

@Service
public class WithdrawalService {
	private static final Logger log = LoggerFactory.getLogger(WithdrawalService.class);

	private final WithdrawalRepository withdrawalRepository;

	public WithdrawalService(WithdrawalRepository withdrawalRepository) {
		this.withdrawalRepository = withdrawalRepository;
	}

	public void registerReason(WithdrawalRequestDto reason) {
		withdrawalRepository.save(reason.newWithdrawal());
	}
}

