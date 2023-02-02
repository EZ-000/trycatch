package com.ssafy.trycatch.user.service;

import com.ssafy.trycatch.common.service.CrudService;
import com.ssafy.trycatch.user.domain.Withdrawal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.ssafy.trycatch.user.controller.dto.WithdrawalRequestDto;
import com.ssafy.trycatch.user.domain.WithdrawalRepository;

@Slf4j
@Service
public class WithdrawalService extends CrudService<Withdrawal, Long, WithdrawalRepository> {
	public WithdrawalService(WithdrawalRepository withdrawalRepository) {
		super(withdrawalRepository);
	}

	public void registerReason(WithdrawalRequestDto reason) {
		repository.save(reason.newWithdrawal());
	}
}

