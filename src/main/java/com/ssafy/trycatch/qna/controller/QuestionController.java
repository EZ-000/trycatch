package com.ssafy.trycatch.qna.controller;

import com.ssafy.trycatch.qna.controller.dto.CreateQuestionRequestDto;
import com.ssafy.trycatch.qna.controller.dto.CreateQuestionResponseDto;
import com.ssafy.trycatch.qna.controller.dto.FindQuestionResponseDto;
import com.ssafy.trycatch.qna.controller.dto.PutQuestionRequestDto;
import com.ssafy.trycatch.qna.domain.Question;
import com.ssafy.trycatch.qna.domain.QuestionRepository;
import com.ssafy.trycatch.qna.service.CategoryService;
import com.ssafy.trycatch.qna.service.QuestionService;
import com.ssafy.trycatch.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/${apiPrefix}/question")
public class QuestionController {

    private final QuestionService questionService;
    private final CategoryService categoryService;
    private final UserService userService;
    private final QuestionRepository questionRepository;

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

    /**
     Question 엔티티 리스트를 FindQuestionResponseDto 리스트로 변환하여 반환
     */
    @GetMapping
    public ResponseEntity<List<FindQuestionResponseDto>> findAllQuestions(
            @PageableDefault Pageable pageable
    ) {
        List<Question> entities = questionService.findAllQuestions(pageable);
        List<FindQuestionResponseDto> questions = entities.stream()
                .map(FindQuestionResponseDto::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(questions);
    }

    /**
     PutQuestionRequestDto로 받은 수정 요청을 처리하고,
     CreateQuestionResponseDto로 반환
     */
    @PutMapping
    public ResponseEntity<CreateQuestionResponseDto> putQuestion (
            @RequestBody @Valid PutQuestionRequestDto putQuestionRequestDto
    ) {
        final Question entity = questionService.putQuestion(putQuestionRequestDto);
        return ResponseEntity.ok(CreateQuestionResponseDto.from(entity));
    }

    @DeleteMapping
    public ResponseEntity<String> deleteQuestion (Long questionId) {
        questionService.deleteQuestion(questionId);
        return ResponseEntity.status(HttpStatus.OK).body("정상적으로 삭제되었습니다.");
    }

    @Autowired
    public QuestionController(
            QuestionService questionService,
            CategoryService categoryService,
            UserService userService,
            QuestionRepository questionRepository) {
        this.questionService = questionService;
        this.categoryService = categoryService;
        this.userService = userService;
        this.questionRepository = questionRepository;
    }
}
