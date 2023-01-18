package com.ssafy.trycatch.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/${apiPrefix}/answer")
public class AnswerController {
	@GetMapping("/{contentId}")
	public ResponseEntity<String> getAnswers(@PathVariable String contentId) {
		return ResponseEntity.ok("contentId에 해당하는 글의 답변을 조회합니다.");
	}

	@PostMapping("/{contentId}")
	public ResponseEntity<String> createAnswers(@PathVariable String contentId) {
		return ResponseEntity.ok("contentId에 해당하는 글에 답변을 등록합니다.");
	}

}
