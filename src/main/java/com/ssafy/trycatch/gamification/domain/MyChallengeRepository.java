package com.ssafy.trycatch.gamification.domain;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MyChallengeRepository extends JpaRepository<MyChallenge, Long> {
    Optional<MyChallenge> findFirstByChallenge_IdAndUser_IdOrderByIdDesc(Long challengeId, Long userId);
    List<MyChallenge> findByUser_IdAndStatusInfo(Long id, StatusInfo statusInfo, Pageable pageable);
    }