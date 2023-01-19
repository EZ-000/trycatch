package com.ssafy.trycatch.qna;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/${apiPrefix}/qna")
public class QNAController {
	@GetMapping()
	public ResponseEntity<String> findQNAs() {
		return ResponseEntity.ok("전체 질문 리스트를 조회합니다.");
	}

	@PostMapping()
	public ResponseEntity<String> createQuestion() {
		return ResponseEntity.ok("질문을 생성합니다.");
	}

	@GetMapping("/{id}")
	public ResponseEntity<String> readQNA(@PathVariable Long id) {
		return ResponseEntity.ok("질문 id에 해당하는 글과 그 글에 달린 답변을 조회합니다.");
	}

	@PutMapping("/{id}")
	public ResponseEntity<String> putQNA(@PathVariable Long id) {
		return ResponseEntity.ok("질문 id에 해당하는 글을 수정합니다.");
	}

	@PostMapping("/ec")
	public ResponseEntity<String> suggestQNAs() {
		return ResponseEntity.ok("사용자가 입력한 에러코드를 바탕으로 비슷한 질문리스트를 반환합니다.");
	}

	@PostMapping("/{contentId}/{commentId}")
	public ResponseEntity<String> acceptAnswer(@PathVariable Long commentId, @PathVariable Long contentId) {
		return ResponseEntity.ok("contentId에 해당하는 글에서 commentId에 해당하는 답변을 채택합니다.");
	}

	@PostMapping("/like")
	public ResponseEntity<String> like() {
		return ResponseEntity.ok("좋아요합니다.");
	}

	@PutMapping("/like")
	public ResponseEntity<String> unlike() {
		return ResponseEntity.ok("좋아요를 취소합니다.");
	}

	@GetMapping("/search")
	public ResponseEntity<String> search() {
		return ResponseEntity.ok("Q&A나 피드 등의 타입 구분하여 원하는 필터로 검색합니다.");
	}

	@GetMapping("/rank/list")
	public ResponseEntity<String> findRanks() {
		return ResponseEntity.ok("가장 높은 포인트를 얻은 사람 목록을 조회합니다.");
	}

	@GetMapping("/bookmark")
	public ResponseEntity<String> findBookmarks() {
		return ResponseEntity.ok("사용자의 즐겨찾기 리스트를 조회합니다.");
	}

	@PostMapping("/bookmark")
	public ResponseEntity<String> createBookmark() {
		return ResponseEntity.ok("원하는 기술 블로그 글을 즐겨찾기할 수 있습니다.");
	}

	@PutMapping("/bookmark")
	public ResponseEntity<String> removeBookmark() {
		return ResponseEntity.ok("즐겨찾기를 취소할 수 있습니다.");
	}

	@GetMapping("/answer/{contentId}")
	public ResponseEntity<String> findAnswers(@PathVariable Long contentId) {
		return ResponseEntity.ok("contentId에 해당하는 글의 답변을 조회합니다.");
	}

	@PostMapping("/answer/{contentId}")
	public ResponseEntity<String> createAnswers(@PathVariable Long contentId) {
		return ResponseEntity.ok("contentId에 해당하는 글에 답변을 등록합니다.");
	}
}
