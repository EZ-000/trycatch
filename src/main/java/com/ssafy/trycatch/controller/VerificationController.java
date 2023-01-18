package com.ssafy.trycatch.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/${apiPrefix}/verification")
public class VerificationController {
	@GetMapping("/{userId}")
	public ResponseEntity<String> isConfirmed(@PathVariable String userId) {
		return ResponseEntity.ok("사용자의 기업 이메일 인증 여부를 확인합니다.");
	}

	@PostMapping("/{userId}")
	public ResponseEntity<String> verifyCompany(@PathVariable String userId) {
		return ResponseEntity.ok("회사 인증 이메일을 전송합니다.");
	}
}
