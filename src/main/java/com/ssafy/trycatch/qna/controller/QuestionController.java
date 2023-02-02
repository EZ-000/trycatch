package com.ssafy.trycatch.qna.controller;

import com.ssafy.trycatch.common.domain.TargetType;
import com.ssafy.trycatch.common.infra.config.jwt.TokenService;
import com.ssafy.trycatch.common.service.BookmarkService;
import com.ssafy.trycatch.common.service.CompanyService;
import com.ssafy.trycatch.common.service.LikesService;
import com.ssafy.trycatch.qna.controller.dto.*;
import com.ssafy.trycatch.qna.domain.Answer;
import com.ssafy.trycatch.qna.domain.Question;
import com.ssafy.trycatch.qna.service.AnswerService;
import com.ssafy.trycatch.qna.service.QuestionService;
import com.ssafy.trycatch.user.domain.User;
import com.ssafy.trycatch.user.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/${apiPrefix}/question")
public class QuestionController {
    private final QuestionService questionService;
    private final AnswerService answerService;
    private final UserService userService;
    private final TokenService tokenService;
    private final CompanyService companyService;
    private final LikesService likesService;
    private final BookmarkService bookmarkService;

    @Autowired
    public QuestionController(
            QuestionService questionService,
            AnswerService answerService,
            UserService userService,
            TokenService tokenService, CompanyService companyService, LikesService likesService, BookmarkService bookmarkService) {
        this.questionService = questionService;
        this.answerService = answerService;
        this.userService = userService;
        this.tokenService = tokenService;
        this.companyService = companyService;
        this.likesService = likesService;
        this.bookmarkService = bookmarkService;
    }

    /**
     Question 엔티티 리스트를 FindQuestionResponseDto 리스트로 변환하여 반환
     */
    @GetMapping
    public ResponseEntity<List<?>> findAllQuestions(
            @PageableDefault Pageable pageable,
            @RequestHeader(value = "Authorization", defaultValue = "NONE") String token ) {
        List<Question> entities = questionService.findAllQuestions(pageable);
        if (Objects.equals(token, "NONE")) {
            List<FindQuestionResponseNotLoginDto> questions = entities.stream()
                    .map(question -> {
                        List<Answer> answers = answerService.findByQuestionId(question.getId());
                        return FindQuestionResponseNotLoginDto.from(question, answers, companyService);
                    })
                    .collect(Collectors.toList());
            return ResponseEntity.ok(questions);
        }
        else {
            List<FindQuestionResponseDto> questions = entities.stream()
                    .map(question -> {
                        final User user = userService.findUserById(Long.parseLong(tokenService.getUid(token)));
                        final List<Answer> answers = answerService.findByQuestionId(question.getId());
                        final TargetType type = TargetType.QUESTION;
                        final Boolean isLiked = Optional.ofNullable(likesService.getLikes(user.getId(), question.getId(), type).getActivated())
                                .orElse(false);
                        final Boolean isBookmarked = Optional.ofNullable(bookmarkService.getBookmark(user.getId(), question.getId(), type).getActivated())
                                .orElse(false);
                        return FindQuestionResponseDto.from(question, answers, user, companyService, isLiked, isBookmarked);
                    })
                    .collect(Collectors.toList());
            return ResponseEntity.ok(questions);
        }
    }

    /**
     * {@code Question} 엔티티를 생성 후 데이터베이스에 저장
     * @param createQuestionRequestDto 질문 생성 요청 DTO
     */
    @PostMapping
    public ResponseEntity<CreateQuestionResponseDto> createQuestion(
            @RequestHeader(value = "Authorization", defaultValue = "NONE") String token,
            @RequestBody CreateQuestionRequestDto createQuestionRequestDto
    ) {
        final User user = userService.findUserById(Long.parseLong(tokenService.getUid(token)));
        final Question newEntity = createQuestionRequestDto.newQuestion(user);
        final Question savedEntity = questionService.saveQuestion(newEntity);
        final TargetType type = TargetType.QUESTION;
        final Boolean isLiked = Optional.ofNullable(likesService.getLikes(user.getId(), savedEntity.getId(), type).getActivated())
                .orElse(false);
        final Boolean isBookmarked = Optional.ofNullable(bookmarkService.getBookmark(user.getId(), savedEntity.getId(), type).getActivated())
                .orElse(false);
        return ResponseEntity.ok(CreateQuestionResponseDto.from(savedEntity, companyService, isLiked, isBookmarked));
    }

    /**
     PutQuestionRequestDto로 받은 수정 요청을 처리하고,
     CreateQuestionResponseDto로 반환
     */
    @PutMapping("/{questionId}")
    public ResponseEntity<CreateQuestionResponseDto> putQuestion (
            @RequestBody @Valid PutQuestionRequestDto putQuestionRequestDto
    ) {
        final Question entity = questionService.putQuestion(putQuestionRequestDto);
        return ResponseEntity.ok(CreateQuestionResponseDto.from(entity, companyService, false, false));
    }

    @DeleteMapping("/questionId")
    public ResponseEntity<String> deleteQuestion (Long questionId) {
        questionService.deleteQuestion(questionId);
        return ResponseEntity.ok().build();
    }

    /**
     * {@code Question#id}로 {@code Question}을 찾아 {@code QuestionResponseDto}로 반환하여 반환
     * @param questionId {@code Question}의 id
     */
    @GetMapping("/{questionId}")
    public ResponseEntity<?> findQuestionById(
            @PathVariable("questionId") Long questionId,
            @RequestHeader(value = "Authorization", defaultValue = "NONE") String token
    ) {
        final Question before = questionService.findQuestionById(questionId);
        final List<Answer> answers = answerService.findByQuestionId(before.getId());
        if (Objects.equals(token, "NONE")) {
            final FindQuestionResponseNotLoginDto question = FindQuestionResponseNotLoginDto.from(before, answers, companyService);
            return ResponseEntity.ok(question);
        }
        else {
            final User user = userService.findUserById(Long.parseLong(tokenService.getUid(token)));
            final TargetType type = TargetType.QUESTION;
            final Boolean isLiked = Optional.ofNullable(likesService.getLikes(user.getId(), before.getId(), type).getActivated())
                    .orElse(false);
            final Boolean isBookmarked = Optional.ofNullable(bookmarkService.getBookmark(user.getId(), before.getId(), type).getActivated())
                    .orElse(false);
            final FindQuestionResponseDto question = FindQuestionResponseDto.from(before, answers, user, companyService, isLiked, isBookmarked);
            return ResponseEntity.ok(question);
        }
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

    // MOCK API: 북마크한 질문 조회
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

    // MOCK API: 질문 북마크
    @PostMapping("bookmark/{questionId}")
    public ResponseEntity<BookmarkQuestionResponseDto> bookmark(
            @PathVariable("questionId") Long questionId
    )
    {
        final Question entity = questionService.findQuestionById(questionId);
        return ResponseEntity.ok(BookmarkQuestionResponseDto.from(entity));
    }

    // MOCK API: 질문 북마크 취소
    @PutMapping("/bookmark/{questionId}")
    public ResponseEntity<BookmarkQuestionResponseDto> removeBookmark(
            @PathVariable("questionId") Long questionId
    )
    {
        final Question entity = questionService.findQuestionById(questionId);
        return ResponseEntity.ok(BookmarkQuestionResponseDto.from(entity));
    }

    // MOCK API: 답변 채택
    @PostMapping("/{questionId}/{answerId}")
    public ResponseEntity<AcceptQuestionResponseDto> acceptAnswer(@PathVariable Long questionId, @PathVariable Long answerId) {
        final Question entity = questionService.findQuestionById(questionId);
        return ResponseEntity.ok(AcceptQuestionResponseDto.from(entity));
    }

    // MOCK API: 에러코드 기반 질문 추천
    @GetMapping("/ec")
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

    // MOCK API: 질문 좋아요 취소
    @PutMapping("/{questionId}/like")
    public ResponseEntity<LikeQuestionResponseDto> Unlike(
            @PathVariable("questionId") Long questionId
    )
    {
        final Question entity = questionService.findQuestionById(questionId);
        return ResponseEntity.ok(LikeQuestionResponseDto.from(entity));
    }



}
