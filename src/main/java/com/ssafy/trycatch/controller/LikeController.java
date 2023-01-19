package com.ssafy.trycatch.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/${apiPrefix}/like")
public class LikeController {
	@PostMapping()
	public ResponseEntity<String> like() {
		return ResponseEntity.ok("좋아요합니다.");
	}

	@PutMapping()
	public ResponseEntity<String> unlike() {
		return ResponseEntity.ok("좋아요를 취소합니다.");
	}
}
