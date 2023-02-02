package com.ssafy.trycatch.gamification.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MyBadgeRepository extends JpaRepository<MyBadge, Long> {
}