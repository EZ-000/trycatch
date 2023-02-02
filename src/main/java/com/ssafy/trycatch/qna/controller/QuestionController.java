package com.ssafy.trycatch.qna.controller;

import com.ssafy.trycatch.common.domain.TargetType;
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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Nullable;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/${apiPrefix}/question")
public class QuestionController {
    private final QuestionService questionService;
    private final AnswerService answerService;
    private final UserService userService;
    private final CompanyService companyService;
    private final LikesService likesService;
    private final BookmarkService bookmarkService;

    @Autowired
    public QuestionController(
            QuestionService questionService, AnswerService answerService, UserService userService, CompanyService companyService, LikesService likesService, BookmarkService bookmarkService) {
        this.questionService = questionService;
        this.answerService = answerService;
        this.userService = userService;
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
            @Nullable @AuthenticationPrincipal Long userId ) {
        List<Question> entities = questionService.findAllQuestions(pageable);
        if (userId == null) {
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
                        final User user = userService.findUserById(userId);
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
            @Nullable @AuthenticationPrincipal Long userId,
            @RequestBody CreateQuestionRequestDto createQuestionRequestDto
    ) {
        final User user = userService.findUserById(userId);
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
    public ResponseEntity putQuestion (
            @RequestBody @Valid PutQuestionRequestDto putQuestionRequestDto
    ) {
        final Question entity = questionService.putQuestion(putQuestionRequestDto);
        return ResponseEntity.ok().build();
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
            @Nullable @AuthenticationPrincipal Long userId
    ) {
        final Question before = questionService.findQuestionById(questionId);
        final List<Answer> answers = answerService.findByQuestionId(before.getId());
        // 조회수
        before.setViewCount(before.getViewCount() + 1);
        questionService.saveQuestion(before);
        if (userId == null) {
            final FindQuestionResponseNotLoginDto question = FindQuestionResponseNotLoginDto.from(before, answers, companyService);
            return ResponseEntity.ok(question);
        }
        else {
            final User user = userService.findUserById(userId);
            final TargetType type = TargetType.QUESTION;
            final Boolean isLiked = Optional.ofNullable(likesService.getLikes(user.getId(), before.getId(), type).getActivated())
                    .orElse(false);
            final Boolean isBookmarked = Optional.ofNullable(bookmarkService.getBookmark(user.getId(), before.getId(), type).getActivated())
                    .orElse(false);
            final FindQuestionResponseDto question = FindQuestionResponseDto.from(before, answers, user, companyService, isLiked, isBookmarked);
            return ResponseEntity.ok(question);
        }
    }

    @PostMapping("/{questionId}/answer")
    public ResponseEntity createAnswers(
            @PathVariable Long questionId,
            @RequestBody CreateAnswerRequestDto createAnswerRequestDto,
            @Nullable @AuthenticationPrincipal Long userId
    ) {
        final Question question = questionService.findQuestionById(questionId);
        final User user = userService.findUserById(userId);
        final Answer newAnswer = createAnswerRequestDto.newAnswer(question, user);
        final Answer savedAnswer = answerService.saveAnswer(newAnswer);
        return ResponseEntity.ok().build();
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
}
