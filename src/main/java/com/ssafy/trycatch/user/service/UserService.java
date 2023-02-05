package com.ssafy.trycatch.user.service;

import com.ssafy.trycatch.common.domain.Bookmark;
import com.ssafy.trycatch.user.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.trycatch.common.service.CrudService;
import com.ssafy.trycatch.feed.domain.ReadRepository;
import com.ssafy.trycatch.user.service.exceptions.UserNotFoundException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService extends CrudService<User, Long, UserRepository> {
	private final ReadRepository readRepository;
	private final WithdrawalRepository withdrawalRepository;

	public UserService(
			UserRepository repository,
			ReadRepository readRepository,
			WithdrawalRepository withdrawalRepository
	) {
		super(repository);
		this.readRepository = readRepository;
		this.withdrawalRepository = withdrawalRepository;
	}

	public User findUserById(Long userId) {
		return repository.findById(userId).orElseThrow(UserNotFoundException::new);
	}

	@Transactional
	public void inActivateUser(long uid, Withdrawal reason) {
		User savedUser = repository.findById(uid).orElseThrow();
		savedUser.setActivated(false);
		repository.save(savedUser);
		withdrawalRepository.save(reason);
	}

	public Long findNameToId(String userName) {
		return repository.findByUsername(userName).orElseThrow(UserNotFoundException::new).getId();
	}

	public User findUserByName(String userName) {
		return repository.findByUsername(userName).orElseThrow(UserNotFoundException::new);
	}

	public User getDetailUserInfo(Long userId) {
		return repository.findById(userId).orElseThrow(UserNotFoundException::new);
	}
}