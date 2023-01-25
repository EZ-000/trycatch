package com.ssafy.trycatch.qna.controller;

import com.ssafy.trycatch.qna.controller.dto.*;
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

    // MOCK API: 질문 검색
    @GetMapping("/search")
    public ResponseEntity<List<SearchQuestionResponseDto>> search(
            @PageableDefault Pageable pageable
    ) {
        List<Question> entities = questionService.findAllQuestions(pageable);
        List<SearchQuestionResponseDto> questions = entities.stream()
                .map(SearchQuestionResponseDto::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(questions);
    }

    // MOCK API: 질문 북마크
    @GetMapping("/bookmark")
    public ResponseEntity<List<BookmarkQuestionResponseDto>> findBookmarks(
            @PageableDefault Pageable pageable
    ) {
        List<Question> entities = questionService.findAllQuestions(pageable);
        List<BookmarkQuestionResponseDto> questions = entities.stream()
                .map(BookmarkQuestionResponseDto::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(questions);
    }

    // MOCK API: 답변 채택
    @PostMapping("/{questionId}/{answerId}")
    public ResponseEntity<AcceptQuestionResponseDto> acceptAnswer(@PathVariable Long questionId, @PathVariable Long answerId) {
        final Question entity = questionService.findQuestionById(questionId);
        return ResponseEntity.ok(AcceptQuestionResponseDto.from(entity));
    }

    // MOCK API: 에러코드 기반 질문 추천
    @PostMapping("/ec")
    public ResponseEntity<List<SuggestQuestionResponseDto>> suggestQuestions(
            @PageableDefault Pageable pageable
    ) {
        List<Question> entities = questionService.findAllQuestions(pageable);
        List<SuggestQuestionResponseDto> questions = entities.stream()
                .map(SuggestQuestionResponseDto::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(questions);
    }

    // MOCK API: 질문 좋아요
    @PostMapping("{questionId}/like")
    public ResponseEntity<LikeQuestionResponseDto> like(
            @PathVariable("questionId") Long questionId
    )
    {
        final Question entity = questionService.findQuestionById(questionId);
        return ResponseEntity.ok(LikeQuestionResponseDto.from(entity));
    }

    // MOCK API: 질문 북마크
    @PostMapping("{questionId}/bookmark")
    public ResponseEntity<BookmarkQuestionResponseDto> bookmark(
            @PathVariable("questionId") Long questionId
    )
    {
        final Question entity = questionService.findQuestionById(questionId);
        return ResponseEntity.ok(BookmarkQuestionResponseDto.from(entity));
    }



    // MOCK API: 질문 좋아요 취소
    @PutMapping("/like")
    public ResponseEntity<LikeQuestionResponseDto> Unlike(
            @PathVariable("questionId") Long questionId
    )
    {
        final Question entity = questionService.findQuestionById(questionId);
        return ResponseEntity.ok(LikeQuestionResponseDto.from(entity));
    }

    // MOCK API: 질문 북마크 취소
    @PutMapping("/bookmark")
    public ResponseEntity<BookmarkQuestionResponseDto> removeBookmark(
            @PathVariable("questionId") Long questionId
    )
    {
        final Question entity = questionService.findQuestionById(questionId);
        return ResponseEntity.ok(BookmarkQuestionResponseDto.from(entity));
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
