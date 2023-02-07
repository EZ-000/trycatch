package com.ssafy.trycatch.user.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByGithubNodeId(String githubNodeId);

    Optional<User> findByUsername(String username);
}