package com.ssafy.trycatch.qna.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/${apiPrefix}/answer")
public class AnswerController {
    @PostMapping("/{answerId}")
    public ResponseEntity<String> createAnswers(@PathVariable Long answerId) {
        return ResponseEntity.ok("contentId에 해당하는 글에 답변을 등록합니다.");
    }

    @GetMapping("/{answerId}")
    public ResponseEntity<String> findAnswers(@PathVariable Long answerId) {
        return ResponseEntity.ok("contentId에 해당하는 글의 답변을 조회합니다.");
    }

    @PostMapping("/{answerId}/like")
    public ResponseEntity<String> like(@PathVariable Long answerId) {
        return ResponseEntity.ok("좋아요합니다.");
    }

    @PutMapping("/{answerId}/unlike")
    public ResponseEntity<String> unlike(@PathVariable Long answerId) {
        return ResponseEntity.ok("좋아요를 취소합니다.");
    }

}
