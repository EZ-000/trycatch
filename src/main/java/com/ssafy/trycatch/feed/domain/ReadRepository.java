package com.ssafy.trycatch.feed.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReadRepository extends JpaRepository<Read, Long> {
	//List<Read> findTop10ByUserIdOrderByIdDesc(Long id);

	@Query("select r from Read r where r.user.id = ?1 and (r.user.id,r.readAt) in (select sr.user.id, max(sr.readAt) from Read sr group by sr.user.id, sr.feed.id) order by r.id DESC limit 10")
	List<Read> findTop10ByUserIdOrderByIdDesc(Long id);
	}