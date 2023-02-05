package com.ssafy.trycatch.user.domain;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByGithubNodeId(String githubNodeId);

    Optional<User> findByUsername(String username);
}