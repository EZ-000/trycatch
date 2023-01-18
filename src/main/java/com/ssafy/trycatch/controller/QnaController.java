package com.ssafy.trycatch.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/${apiPrefix}/qna")
public class QnaController {
	@GetMapping()
	public String readQna() {
		return "전체 질문 리스트를 조회합니다.";
	}

	@PostMapping()
	public String createCodeQuestion() {
		return "코드 관련 질문을 생성합니다.";
	}

	@GetMapping("/{id}")
	public String readQna(@PathVariable String id) {
		return "질문 id에 해당하는 글과 그 글에 달린 답변을 조회합니다.";
	}

	@PutMapping("/{id}")
	public String putQna(@PathVariable String id) {
		return "질문 id에 해당하는 글을 수정합니다.";
	}

	@PostMapping("/ec")
	public String suggestQna() {
		return "사용자가 입력한 에러코드를 바탕으로 비슷한 질문리스트를 반환합니다.";
	}

	@PostMapping("/{contentId}/{commentId}")
	public String acceptAnswer() {
		return "contentId에 해당하는 글에서 commentId에 해당하는 답변을 채택합니다.";
	}
}
