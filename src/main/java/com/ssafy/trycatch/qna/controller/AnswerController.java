package com.ssafy.trycatch.qna.controller;

import com.ssafy.trycatch.qna.controller.dto.CreateAnswerResponseDto;
import com.ssafy.trycatch.qna.controller.dto.FindAnswerResponseDto;
import com.ssafy.trycatch.qna.controller.dto.FindQuestionResponseDto;
import com.ssafy.trycatch.qna.domain.Answer;
import com.ssafy.trycatch.qna.domain.Question;
import com.ssafy.trycatch.qna.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/${apiPrefix}/answer")
public class AnswerController {
    private final AnswerService answerService;

    @GetMapping("/{answerId}")
    public ResponseEntity<FindAnswerResponseDto> findAnswerById (
            @PathVariable("answerId") Long answerId
    ) {
        final Answer entity = answerService.findAnswerById(answerId);
        return ResponseEntity.ok(FindAnswerResponseDto.from(entity));
    }

    // MOCK API: 답변 생성
    @PostMapping("/{questionId}")
    public ResponseEntity<String> createAnswers(
            @PathVariable Long questionId
    ) {
        return ResponseEntity.ok("좋아요합니다.");
    }

    // MOCK API: 답변 좋아요
    @PostMapping("/{answerId}/like")
    public ResponseEntity<String> like(@PathVariable Long answerId) {
        return ResponseEntity.ok("좋아요합니다.");
    }

    // MOCK API: 답변 좋아요 취소
    @PutMapping("/{answerId}/unlike")
    public ResponseEntity<String> unlike(@PathVariable Long answerId) {
        return ResponseEntity.ok("좋아요를 취소합니다.");
    }

    @Autowired
    public AnswerController (AnswerService answerService) {
        this.answerService = answerService;
    }
}
