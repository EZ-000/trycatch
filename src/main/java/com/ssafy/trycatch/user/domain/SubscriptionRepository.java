package com.ssafy.trycatch.user.domain;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

	Optional<Object> findByUserIdAndCompanyId(Long id, Long id1);
}