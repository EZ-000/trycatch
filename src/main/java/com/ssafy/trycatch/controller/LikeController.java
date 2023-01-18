package com.ssafy.trycatch.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
// 나눌게요 ~ ~ ~ ~ ~ ! ! ! ! !
@RestController
@RequestMapping("/${apiPrefix}/like")
public class LikeController {
	@PostMapping()
	public String like() {
		return "좋아요합니다.";
	}

	@PutMapping()
	public String unlike() {
		return "좋아요를 취소합니다.";
	}
}
