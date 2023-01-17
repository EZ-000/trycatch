package com.ssafy.trycatch.qna;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/qna")
public class QuestionController {
	@GetMapping()
	public String readQna() {
		return "전체 질문 리스트를 조회합니다.";
	}

	@PostMapping()
	public String createCodeQuestion() {
		return "코드 관련 질문을 생성합니다.";
	}




}
