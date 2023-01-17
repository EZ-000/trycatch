package com.ssafy.trycatch.user;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
	@GetMapping("/verification/{userId}")
	public String isConfirmed() {
		return "사용자의 기업 이메일 인증 여부를 확인합니다.";
	}

	@PostMapping("/verification/{userId}")
	public String confirmCompany() {
		return "회사 인증 이메일을 전송합니다.";
	}

	@GetMapping("/user/{userId}")
	public String readUser() {
		return "사용자를 조회합니다.";
	}

	@PatchMapping("/user/{userId}")
	public String patchUser() {
		return "사용자 정보를 수정합니다.";
	}

	@DeleteMapping("user/{userId}")
	public String deleteUser() {
		return "사용자가 탈퇴합니다. 단, 테이블에서는 활성 상태가 수정됩니다.";
	}

	@GetMapping("user/{userId}/list")
	public String readFollow() {
		return "사용자의 팔로우/팔로워 목록을 조회합니다.";
	}
}
