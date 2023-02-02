package com.ssafy.trycatch.user.service;

import com.ssafy.trycatch.common.service.CrudService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.trycatch.user.domain.User;
import com.ssafy.trycatch.user.domain.UserRepository;
import com.ssafy.trycatch.user.service.exceptions.UserNotFoundException;

@Slf4j
@Service
public class UserService extends CrudService<User, Long, UserRepository> {

	@Autowired
	public UserService(UserRepository userRepository) {
		super(userRepository);
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
}