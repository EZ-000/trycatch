package com.ssafy.trycatch.gamification.domain;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MyChallengeRepository extends JpaRepository<MyChallenge, Long> {
    boolean existsByChallenge_IdAndUser_Id(Long challengeId, Long userId);

    Optional<MyChallenge> findByChallenge_IdAndUser_Id(Long challengeId, Long userId);

    List<MyChallenge> findByUser_IdOrderByStartFromDesc(Long id, Pageable pageable);



    

}