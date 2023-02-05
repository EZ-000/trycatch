package com.ssafy.trycatch.user.service;

import com.ssafy.trycatch.common.service.CrudService;
import com.ssafy.trycatch.user.domain.Withdrawal;
import com.ssafy.trycatch.user.domain.WithdrawalRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class WithdrawalService extends CrudService<Withdrawal, Long, WithdrawalRepository> {
    public WithdrawalService(WithdrawalRepository withdrawalRepository) {
        super(withdrawalRepository);
    }
}

