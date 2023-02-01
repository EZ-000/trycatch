package com.ssafy.trycatch.user.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.trycatch.user.domain.User;
import com.ssafy.trycatch.user.domain.UserRepository;
import com.ssafy.trycatch.user.service.exceptions.UserNotFoundException;

import java.security.Principal;

@Service
public class UserService {

	private static final Logger log = LoggerFactory.getLogger(UserService.class);

	private final UserRepository userRepository;

	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public User findUserById(Long userId) {
		return userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
	}

	public void inActivateUser(long parseLong) {
		User savedUser = userRepository.findById(parseLong).orElseThrow();
		savedUser.setActivated(false);
		userRepository.save(savedUser);
	}

	public Long findNameToId(String userName) {
		return userRepository.findByUsername(userName).orElseThrow(UserNotFoundException::new).getId();
	}
}