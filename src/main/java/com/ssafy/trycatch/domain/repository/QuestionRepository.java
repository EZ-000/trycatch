package com.ssafy.trycatch.domain.repository;

import com.ssafy.trycatch.domain.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {

}
