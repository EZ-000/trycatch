package com.ssafy.trycatch.user.service;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ssafy.trycatch.user.controller.dto.WithdrawalRequestDto;
import com.ssafy.trycatch.user.domain.User;
import com.ssafy.trycatch.user.domain.UserRepository;

@SpringBootTest
class UserServiceTest {
	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;

	@Test
	@DisplayName("--- inActive Test ---")
	public void inActive() {
		final Long beforeCount = userRepository.count();
		final Long userId = 3L;
		User addUser = User.builder()
			.id(userId)
			.activated(true)
			.company(null)
			.points(0)
			.createdAt(LocalDate.now())
			.build();
		userRepository.save(addUser);

		final Long afterInsertCount = userRepository.count();
		assertEquals(beforeCount + 1, afterInsertCount);

		User beforeInactiveUser = userRepository.findById(userId).orElseThrow();
		assertEquals(beforeInactiveUser.getActivated(), true);

		userService.inActivateUser(userId, WithdrawalRequestDto.builder()
			.userId(userId)
			.content("TEST").build().toEntity());

		User afterInactiveUser = userRepository.findById(userId).orElseThrow();
		assertEquals(afterInactiveUser.getActivated(), false);
	}
}