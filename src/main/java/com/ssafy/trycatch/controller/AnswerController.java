package com.ssafy.trycatch.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/${apiPrefix}/answer")
public class AnswerController {
	@GetMapping("/{contentId}")
	public ResponseEntity<String> readAnswer() {

		return ResponseEntity.ok("contentId에 해당하는 글의 답변을 조회합니다.");
	}

	@PostMapping("/{contentId}")
	public String createAnswer() {
		return "contentId에 해당하는 글에 답변을 등록합니다.";
	}

}
