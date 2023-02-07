package com.ssafy.trycatch.feed.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReadRepository extends JpaRepository<Read, Long> {
	List<Read> findTop10ByUserIdOrderByIdDesc(Long id);
	}