package com.ssafy.trycatch.user.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.trycatch.common.service.CrudService;
import com.ssafy.trycatch.feed.domain.ReadRepository;
import com.ssafy.trycatch.user.domain.Follow;
import com.ssafy.trycatch.user.domain.User;
import com.ssafy.trycatch.user.domain.UserRepository;
import com.ssafy.trycatch.user.service.exceptions.UserNotFoundException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService extends CrudService<User, Long, UserRepository> {
	private final ReadRepository readRepository;

	@Autowired
	public UserService(UserRepository userRepository,
					   ReadRepository readRepository) {
		super(userRepository);
		this.readRepository = readRepository;
	}

	public User findUserById(Long userId) {
		return repository.findById(userId).orElseThrow(UserNotFoundException::new);
	}

	public void inActivateUser(long parseLong) {
		User savedUser = repository.findById(parseLong).orElseThrow();
		savedUser.setActivated(false);
		repository.save(savedUser);
	}

	public Long findNameToId(String userName) {
		return repository.findByUsername(userName).orElseThrow(UserNotFoundException::new).getId();
	}

	public User findUserByName(String userName) {
		return repository.findByUsername(userName).orElseThrow(UserNotFoundException::new);
	}

	public User getDetailUserInfo(Long userId) {
		User saved = repository.findById(userId).orElseThrow(UserNotFoundException::new);
		Set<Follow> followees = saved.getFollowees();

		return saved;
	}
}