package com.ssafy.trycatch.qna.controller;

import com.ssafy.trycatch.qna.controller.dto.CreateQuestionRequestDto;
import com.ssafy.trycatch.qna.controller.dto.CreateQuestionResponseDto;
import com.ssafy.trycatch.qna.controller.dto.FindQuestionResponseDto;
import com.ssafy.trycatch.qna.domain.Question;
import com.ssafy.trycatch.qna.service.CategoryService;
import com.ssafy.trycatch.qna.service.QuestionService;
import com.ssafy.trycatch.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/${apiPrefix}/question")
public class QuestionController {

    private final QuestionService questionService;
    private final CategoryService categoryService;
    private final UserService userService;


    /**
     * {@code Question#id}로 {@code Question}을 찾아 {@code QuestionResponseDto}로 반환하여 반환
     * @param questionId {@code Question}의 id
     */
    @GetMapping("/{questionId}")
    public ResponseEntity<FindQuestionResponseDto> findQuestionById(
            @PathVariable("questionId") Long questionId
    ) {
        final Question entity = questionService.findQuestionById(questionId);
        return ResponseEntity.ok(FindQuestionResponseDto.from(entity));
    }

    /**
     * {@code Question} 엔티티를 생성 후 데이터베이스에 저장
     * @param createQuestionRequestDto 질문 생성 요청 DTO
     */
    @PostMapping
    public ResponseEntity<CreateQuestionResponseDto> createQuestion(
            @RequestBody CreateQuestionRequestDto createQuestionRequestDto
    ) {
        final Question newEntity = createQuestionRequestDto.newQuestion(categoryService, userService);
        final Question savedEntity = questionService.saveQuestion(newEntity);
        return ResponseEntity.ok(CreateQuestionResponseDto.from(savedEntity));
    }

    @Autowired
    public QuestionController(
            QuestionService questionService,
            CategoryService categoryService,
            UserService userService
    ) {
        this.questionService = questionService;
        this.categoryService = categoryService;
        this.userService = userService;
    }
}
