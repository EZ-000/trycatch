package com.ssafy.trycatch.qna.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

public class AnswerController {
    @PostMapping("/answer/{contentId}")
    public ResponseEntity<String> createAnswers(@PathVariable Long contentId) {
        return ResponseEntity.ok("contentId에 해당하는 글에 답변을 등록합니다.");
    }

    @GetMapping("/answer/{contentId}")
    public ResponseEntity<String> findAnswers(@PathVariable Long contentId) {
        return ResponseEntity.ok("contentId에 해당하는 글의 답변을 조회합니다.");
    }

    @PostMapping("/like")
    public ResponseEntity<String> like() {
        return ResponseEntity.ok("좋아요합니다.");
    }

    @PutMapping("/like")
    public ResponseEntity<String> unlike() {
        return ResponseEntity.ok("좋아요를 취소합니다.");
    }

}
