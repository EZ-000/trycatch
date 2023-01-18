package com.ssafy.trycatch.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/report")
public class ReportController {
	@PostMapping()
	public String report() {
		return "사람, 게시글, 답변, 피드 등을 신고합니다.";
	}
}
