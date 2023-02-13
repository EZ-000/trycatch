package com.ssafy.trycatch.gamification.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MyBadgeRepository extends JpaRepository<MyBadge, Long> {
    List<MyBadge> findByUser_IdAndStatusInfo(Long id, StatusInfo statusInfo);
}