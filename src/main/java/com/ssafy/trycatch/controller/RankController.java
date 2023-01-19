package com.ssafy.trycatch.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/${apiPrefix}/rank")
public class RankController {
	@GetMapping("/list")
	public ResponseEntity<String> getRanks() {
		return ResponseEntity.ok("가장 높은 포인트를 얻은 사람 목록을 조회합니다.");
	}

}
