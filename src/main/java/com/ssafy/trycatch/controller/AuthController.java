package com.ssafy.trycatch.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/${apiPrefix}/auth")
public class AuthController {
	@GetMapping("/login")
	public ResponseEntity<String> login() {

		return ResponseEntity.ok("로그인합니다.");
	}
}
