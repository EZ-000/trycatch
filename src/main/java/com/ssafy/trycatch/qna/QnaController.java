package com.ssafy.trycatch.qna;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping()
public class QnaController {
	@GetMapping("/qna")
	public String readQna() {
		return "전체 질문 리스트를 조회합니다.";
	}

	@PostMapping("/qna")
	public String createCodeQuestion() {
		return "코드 관련 질문을 생성합니다.";
	}

	@GetMapping("/qna/{id}")
	public String readQna(@PathVariable String id) {
		return "질문 id에 해당하는 글과 그 글에 달린 답변을 조회합니다.";
	}

	@PutMapping("/qna/{id}")
	public String putQna(@PathVariable String id) {
		return "질문 id에 해당하는 글을 수정합니다.";
	}

	@PostMapping("/qna/ec")
	public String suggestQna() {
		return "사용자가 입력한 에러코드를 바탕으로 비슷한 질문리스트를 반환합니다.";
	}

	@PostMapping("/qna/select/{contentId}/{commentId}")
	public String acceptAnswer() {
		return "contentId에 해당하는 글에서 commentId에 해당하는 답변을 채택합니다.";
	}

	@GetMapping("/answer/{contentId}")
	public String readAnswer() {
		return "contentId에 해당하는 글의 답변을 조회합니다.";
	}

	@PostMapping("/answer/{contentId}")
	public String createAnswer() {
		return "contentId에 해당하는 글에 답변을 등록합니다.";
	}

	@PostMapping("/like")
	public String like() {
		return "답변을 좋아요합니다.";
	}

	@PutMapping("/like")
	public String unlike() {
		return "답변 좋아요를 취소합니다.";
	}

	@GetMapping("/rank/list")
	public String readRanking() {
		return "가장 높은 포인트를 얻은 사람 목록을 조회합니다.";
	}
}
