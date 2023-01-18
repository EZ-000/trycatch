package com.ssafy.trycatch.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/${apiPrefix}/feed")
public class ArticleController {
    @GetMapping("/list")
    public ResponseEntity<String> getFeed() {
        return ResponseEntity.ok("피드 목록을 최신순으로 반환합니다. 토큰이 있을 경우 연관도 순으로 반영합니다.");
    }
}
