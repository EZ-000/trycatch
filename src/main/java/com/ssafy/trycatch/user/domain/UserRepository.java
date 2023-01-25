package com.ssafy.trycatch.user.domain;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
	Optional<User> findByGithubNodeId(String githubNodeId);
}