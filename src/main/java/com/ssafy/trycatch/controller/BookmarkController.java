package com.ssafy.trycatch.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/${apiPrefix}/bookmark")
public class BookmarkController {
	@GetMapping()
	public ResponseEntity<String> getBookmarks() {
		return ResponseEntity.ok("사용자의 즐겨찾기 리스트를 조회합니다.");
	}

	@PostMapping()
	public ResponseEntity<String> createBookmark() {
		return ResponseEntity.ok("원하는 기술 블로그 글을 즐겨찾기할 수 있습니다.");
	}

	@PutMapping()
	public ResponseEntity<String> removeBookmark() {
		return ResponseEntity.ok("즐겨찾기를 취소할 수 있습니다.");
	}
}
