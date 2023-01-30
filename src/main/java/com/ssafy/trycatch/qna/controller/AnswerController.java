package com.ssafy.trycatch.qna.controller;

import com.ssafy.trycatch.qna.controller.dto.FindAnswerResponseDto;
import com.ssafy.trycatch.qna.domain.Answer;
import com.ssafy.trycatch.qna.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/${apiPrefix}/answer")
public class AnswerController {
    private final AnswerService answerService;

    // MOCK API: 답변 생성
    @PostMapping("/{questionId}")
    public ResponseEntity<String> createAnswers(
            @PathVariable Long questionId
    ) {
        return ResponseEntity.ok("질문에 해당하는 답변 생성");
    }

    // MOCK API: 답변 좋아요
    @PostMapping("/{answerId}/like")
    public ResponseEntity<String> like(@PathVariable Long answerId) {
        return ResponseEntity.ok("좋아요합니다.");
    }

    // MOCK API: 답변 좋아요 취소
    @PutMapping("/{answerId}/like")
    public ResponseEntity<String> unlike(@PathVariable Long answerId) {
        return ResponseEntity.ok("좋아요를 취소합니다.");
    }

    @Autowired
    public AnswerController (AnswerService answerService) {
        this.answerService = answerService;
    }
}
